//
// $Id$
//
// OOO GWT Utils - utilities for creating GWT applications
// Copyright (C) 2009-2010 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/ooo-gwt-utils/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.gwt.util;

import java.util.HashSet;

import static com.threerings.gwt.util.WikiUtils.*;

/**
 * Renders Creole wiki text into XHTML. Adapted for GWT from Java parser. {@link #render} takes
 * wiki text and returns XHTML.
 *
 * <p>WikiParser's behavior can be customized by overriding appendXxx() methods, which should make
 * integration of this class into any wiki/blog/forum software easy and painless.</p>
 *
 * @author Yaroslav Stavnichiy (yarosla@gmail.com)
 * @see http://code.google.com/p/t4-wiki-parser/
 * @see http://www.wikicreole.org/
 */
public class WikiParser
{
    /**
     * Renders the supplied wiki text to XHTML.
     */
    public static String render (String wikiText) {
        return new WikiParser().doRender(wikiText);
    }

    /**
     * Renders the supplied wiki text snippet to XHTML. This method differs from {@link #render} in
     * that it expects a single line of text which may contain inline formatting, but contains no
     * block formatting.
     */
    public static String renderSnippet (String wikiText) {
        return new WikiParser().doRenderSnippet(wikiText);
    }

    protected String doRender (String text) {
        wikiText=preprocessWikiText(text);
        wikiLength=wikiText.length();
        wikiChars=new char[wikiLength];
        wikiText.getChars(0, wikiLength, wikiChars, 0);

        while (parseBlock());
        closeListsAndTables();
        while (mediawikiTableLevel-- > 0) sb.append("</td></tr></table>\n");
        completeTOC();

        return sb.toString();
    }

    protected String doRenderSnippet (String text) {
        wikiText=preprocessWikiText(text);
        wikiLength=wikiText.length();
        wikiChars=new char[wikiLength];
        wikiText.getChars(0, wikiLength, wikiChars, 0);
        parseItem(pos, null, ContextType.PARAGRAPH);
        return sb.toString();
    }

    // protected void appendMacro(String text) {
    //     if ("TOC".equals(text)) {
    //         sb.append("<<<TOC>>>"); // put TOC placeholder for replacing it later with real TOC
    //     }
    //     else {
    //         sb.append("&lt;&lt;&lt;Macro:");
    //         appendText(text);
    //         sb.append("&gt;&gt;&gt;");
    //     }
    // }

    protected void appendLink (String text) {
        String[] link = split(text, '|');
        String uri = link[0].trim();
        String name = (link.length >= 2 && !isEmpty(link[1].trim())) ? link[1] : uri;
        if (isAbsoluteURI(uri)) {
            appendExternalLink(uri, name);
        } else {
            appendInternalLink(uri, name);
        }
    }

    protected void appendExternalLink (String uri, String text) {
        sb.append("<a href=\""+escapeHTML(uri)+"\" rel=\"nofollow\">");
        appendText(text);
        sb.append("</a>");
    }

    protected void appendInternalLink (String uri, String text) {
        sb.append("<a href=\"#\" title=\"Internal link\">");
        appendText(text);
        sb.append("</a>");
    }

    protected void appendImage (String text) {
        String[] link = split(text, '|');
        String uri = link[0].trim();
        String name = (link.length >= 2 && !isEmpty(link[1].trim())) ? link[1] : uri;
        if (isAbsoluteURI(uri)) {
            appendExternalImage(uri, name);
        } else {
            appendInternalImage(uri, name);
        }
    }

    protected void appendExternalImage (String uri, String text) {
        String alt = escapeHTML(unescapeHTML(text));
        sb.append("<img src=\""+escapeHTML(uri)+"\" alt=\""+alt+"\" title=\""+alt+"\" />");
    }

    protected void appendInternalImage (String uri, String text) {
        sb.append("&lt;&lt;&lt;Internal image(?): ");
        appendText(uri + " " + text);
        sb.append("&gt;&gt;&gt;");
    }

    protected void appendText (String text) {
        sb.append(escapeHTML(unescapeHTML(text)));
    }

    protected String generateTOCAnchorId (int hLevel, String text) {
        int i=0;
        String id=(HEADING_ID_PREFIX!=null ? HEADING_ID_PREFIX :
                   "H"+hLevel+"_")+translit(text.replaceAll("<.+?>", "")).trim().replaceAll(
                       "\\s+", "_").replaceAll("[^a-zA-Z0-9_-]", "");
        while (tocAnchorIds.contains(id)) { // avoid duplicates
            i++;
            id=text+"_"+i;
        }
        tocAnchorIds.add(id);
        return id;
    }

    protected void appendTOCItem (int level, String anchorId, String text) {
        if (level>tocLevel) {
            while (level>tocLevel) {
                toc.append("<ul><li>");
                tocLevel++;
            }
        }
        else {
            while (level<tocLevel) {
                toc.append("</li></ul>");
                tocLevel--;
            }
            toc.append("</li>\n<li>");
        }
        toc.append("<a href='#"+anchorId+"'>"+text+"</a>");
    }

    protected void completeTOC () {
        while (0<tocLevel) {
            toc.append("</li></ul>");
            tocLevel--;
        }
        int idx;
        String tocDiv="<div class='toc'>"+toc.toString()+"</div>";
        while ((idx=sb.indexOf("<<<TOC>>>"))>=0) {
            sb.replace(idx, idx+9, tocDiv);
        }
    }

    protected void appendNowiki (String text) {
        sb.append(escapeHTML(replaceString(replaceString(text, "~{{{", "{{{"), "~}}}", "}}}")));
    }

    private void closeListsAndTables () {
        // close unclosed lists
        while (listLevel>=0) {
            sb.append(LIST_CLOSE[LIST_CHARS.indexOf(listLevels[listLevel--])]);
        }
        if (inTable) {
            sb.append("</table>\n");
            inTable=false;
        }
    }

    private boolean parseBlock () {
        pos = skipSpacesToNewline(pos, wikiLength);
        if (pos>=wikiLength) return false;

        char c=wikiChars[pos];

        if (c=='\n') { // blank line => end of list/table; no other meaning
            closeListsAndTables();
            pos++;
            return true;
        }

        if (c=='|') { // table
            if (mediawikiTableLevel>0) {
                int pp=pos+1;
                if (pp<wikiLength) {
                    boolean newRow=false, endTable=false;
                    if(wikiChars[pp]=='-') { // mediawiki-table new row
                        newRow=true;
                        pp++;
                    }
                    else if(wikiChars[pp]=='}') { // mediawiki-table end table
                        endTable=true;
                        pp++;
                    }
                    pp = skipSpacesTabs(pp, wikiLength); // skip spaces
                    // nothing else on the line => it's mediawiki-table markup
                    if (pp==wikiLength || wikiChars[pp]=='\n') {
                        closeListsAndTables(); // close lists if any
                        sb.append(newRow? "</td></tr>\n<tr><td>":
                                  (endTable? "</td></tr></table>\n":"</td>\n<td>"));
                        if (endTable) mediawikiTableLevel--;
                        pos=pp+1;
                        return pp<wikiLength;
                    }
                }
            }

            if (!inTable) {
                closeListsAndTables(); // close lists if any
                sb.append("<table border=\"1\">");
                inTable=true;
            }
            pos=parseTableRow(pos+1);
            return true;
        }
        else {
            if (inTable) {
                sb.append("</table>\n");
                inTable=false;
            }
        }

        if (listLevel>=0 || LIST_CHARS.indexOf(c)>=0) { // lists
            int lc;
            // count list level
            for (lc=0; lc<=listLevel && pos+lc<wikiLength &&
                     wikiChars[pos+lc]==listLevels[lc]; lc++) ;

            if (lc<=listLevel) { // end list block(s)
                do {
                    sb.append(LIST_CLOSE[LIST_CHARS.indexOf(listLevels[listLevel--])]);
                } while (lc<=listLevel);
                // list(s) closed => retry from the same position
                blockquoteBR=true;
                return true;
            }
            else {
                if (pos+lc>=wikiLength) return false;
                char cc=wikiChars[pos+lc];
                int listType=LIST_CHARS.indexOf(cc);
                if (listType>=0 && pos+lc+1<wikiLength && wikiChars[pos+lc+1]!=cc &&
                    listLevel<MAX_LIST_LEVELS) { // new list block
                    sb.append(LIST_OPEN[listType]);
                    listLevels[++listLevel]=cc;
                    blockquoteBR=true;
                    pos=parseListItem(pos+lc+1);
                    return true;
                }
                else if (listLevel>=0) { // list item - same level
                    if (listLevels[listLevel]=='>' || listLevels[listLevel]==':') sb.append('\n');
                    else if (listLevels[listLevel]=='!') sb.append("</div>\n<div class='center'>");
                    else sb.append("</li>\n<li>");
                    pos=parseListItem(pos+lc);
                    return true;
                }
            }
        }

        if (c=='=') { // heading
            int hc;
            // count heading level
            for (hc=1; hc<6 && pos+hc<wikiLength && wikiChars[pos+hc]=='='; hc++) ;
            if (pos+hc>=wikiLength) return false;
            int p = skipSpacesTabs(pos+hc, wikiLength); // skip spaces
            String tagName="h"+(hc+HEADING_LEVEL_SHIFT);
            sb.append("<"+tagName+" id=''>"); // real id to be inserted after parsing this item
            int hStart=sb.length();
            pos=parseItem(p, wikiText.substring(pos, pos+hc), ContextType.HEADER);
            String hText=sb.substring(hStart, sb.length());
            sb.append("</"+tagName+">\n");
            String anchorId=generateTOCAnchorId(hc, hText);
            sb.insert(hStart-2, anchorId);
            appendTOCItem(hc, anchorId, hText);
            return true;
        }
        else if (c=='<' || c =='>') { // <<< is float left, >>> is float right
            if (pos+2 < wikiLength && wikiChars[pos+1] == c && wikiChars[pos+2] == c) {
                pos = skipSpacesTabs(pos+3, wikiLength); // skip whitespace
                String side = (c == '<') ? "left" : "right";
                sb.append("<div style=\"float: " + side + "\">");
                pos = parseItem(pos, null, ContextType.HEADER);
                sb.append("</div>");
                return true;
            }
        }
        else if (c=='{') { // nowiki-block?
            if (pos+2<wikiLength && wikiChars[pos+1]=='{' && wikiChars[pos+2]=='{') {
                int startNowiki=pos+3;
                int endNowiki=findEndOfNowiki(startNowiki);
                int endPos=endNowiki+3;
                if (wikiText.lastIndexOf('\n', endNowiki)>=startNowiki) { // block <pre>
                    if (wikiChars[startNowiki]=='\n') startNowiki++; // skip the very first '\n'
                    if (wikiChars[endNowiki-1]=='\n') endNowiki--; // omit the very last '\n'
                    sb.append("<pre>");
                    appendNowiki(wikiText.substring(startNowiki, endNowiki));
                    sb.append("</pre>\n");
                    pos=endPos;
                    return true;
                }
                // else inline <nowiki> - proceed to regular paragraph handling
            }
            else if (pos+1<wikiLength && wikiChars[pos+1]=='|') { // mediawiki-table?
                int pp = skipSpacesTabs(pos+2, wikiLength); // skip spaces
                if (pp==wikiLength || wikiChars[pp]=='\n') { // yes, it's start of a table
                    sb.append("<table border=\"1\"><tr><td>");
                    mediawikiTableLevel++;
                    pos=pp+1;
                    return pp<wikiLength;
                }
            }
        }
        else if (c=='-' && wikiText.startsWith("----", pos)) {
            int p = skipSpacesTabs(pos+4, wikiLength); // skip spaces
            if (p==wikiLength || wikiChars[p]=='\n') {
                sb.append("\n<hr/>\n");
                pos=p;
                return true;
            }
        }
        else if (c=='~') { // block-level escaping: '*' '-' '#' '>' ':' '!' '|' '='
            if (pos+1<wikiLength) {
                char nc=wikiChars[pos+1];
                // can't be inline markup
                if (nc=='>' || nc==':' || nc=='-' || nc=='|' || nc=='=' || nc=='!') {
                    pos++; // skip '~' and proceed to regular paragraph handling
                    c=nc;
                }
                else if (nc=='*' || nc=='#') { // might be inline markup so need to double check
                    char nnc=pos+2<wikiLength? wikiChars[pos+2]:0;
                    if (nnc!=nc) {
                        pos++; // skip '~' and proceed to regular paragraph handling
                        c=nc;
                    }
                    // otherwise escaping will be done at line level
                }
                else if (nc=='{') { // might be inline {{{ markup so need to double check
                    char nnc=pos+2<wikiLength? wikiChars[pos+2]:0;
                    if (nnc=='|') { // mediawiki-table?
                        pos++; // skip '~' and proceed to regular paragraph handling
                        c=nc;
                    }
                    // otherwise escaping will be done at line level
                }
            }
        }

        { // paragraph handling
            sb.append("<p>");
            pos=parseItem(pos, null, ContextType.PARAGRAPH);
            sb.append("</p>\n");
            return true;
        }
    }

    private int skipSpacesTabs (int start, int end) {
        int pos = start;
        while (pos < end && (wikiChars[pos] == ' ' || wikiChars[pos] == '\t')) pos++;
        return pos;
    }

    private int skipSpacesToNewline (int start, int end) {
        int pos = start;
        while (pos < end && wikiChars[pos] <= ' ' && wikiChars[pos] != '\n') pos++;
        return pos;
    }

    /**
     * Finds first closing '}}}' for nowiki block or span.
     * Skips escaped sequences: '~}}}'.
     *
     * @param startBlock points to first char after '{{{'
     * @return position of first '}' in closing '}}}'
     */
    private int findEndOfNowiki (int startBlock) {
        // NOTE: this method could step back one char from startBlock position
        int endBlock=startBlock-3;
        do {
            endBlock=wikiText.indexOf("}}}", endBlock+3);
            if (endBlock<0) return wikiLength; // no matching '}}}' found
            while (endBlock+3<wikiLength && wikiChars[endBlock+3]=='}')
                endBlock++; // shift to end of sequence of more than 3x'}' (eg. '}}}}}')
        } while (wikiChars[endBlock-1]=='~');
        return endBlock;
    }

    /**
     * Greedy version of findEndOfNowiki().
     * It finds the last possible closing '}}}' before next opening '{{{'.
     * Also uses escapes '~{{{' and '~}}}'.
     *
     * @param startBlock points to first char after '{{{'
     * @return position of first '}' in closing '}}}'
     */
    @SuppressWarnings("unused")
    private int findEndOfNowikiGreedy (int startBlock) {
        // NOTE: this method could step back one char from startBlock position
        int nextBlock=startBlock-3;
        do {
            do {
                nextBlock=wikiText.indexOf("{{{", nextBlock+3);
            } while (nextBlock>0 && wikiChars[nextBlock-1]=='~');
            if (nextBlock<0) nextBlock=wikiLength;
            int endBlock=wikiText.lastIndexOf("}}}", nextBlock);
            if (endBlock>=startBlock && wikiChars[endBlock-1]!='~') return endBlock;
        } while (nextBlock<wikiLength);
        return wikiLength;
    }

    /**
     * @param start points to first char after pipe '|'
     * @return
     */
    private int parseTableRow (int start) {
        if (start>=wikiLength) return wikiLength;

        sb.append("<tr>");
        boolean endOfRow=false;
        do {
            int colspan=0;
            while (start+colspan<wikiLength && wikiChars[start+colspan]=='|') colspan++;
            start+=colspan;
            colspan++;
            boolean th=start<wikiLength && wikiChars[start]=='=';
            start+=(th?1:0);
            // trim whitespace from the start
            while (start<wikiLength && wikiChars[start]<=' ' && wikiChars[start]!='\n') start++;

            if (start>=wikiLength || wikiChars[start]=='\n') { // skip last empty column
                start++; // eat '\n'
                break;
            }

            sb.append(th? "<th":"<td");
            if (colspan>1) sb.append(" colspan=\""+colspan+"\"");
            sb.append('>');
            try {
                parseItemThrow(start, null, ContextType.TABLE_CELL);
            }
            catch (EndOfSubContextException e) { // end of cell
                start=e.position;
                if (start>=wikiLength) endOfRow=true;
                else if (wikiChars[start]=='\n') {
                    start++; // eat '\n'
                    endOfRow=true;
                }
            }
            catch (EndOfContextException e) {
                start=e.position;
                endOfRow=true;
            }
            sb.append(th? "</th>":"</td>");
        } while (!endOfRow/* && start<wikiLength && wikiChars[start]!='\n'*/);
        sb.append("</tr>\n");
        return start;
    }

    /**
     * Same as parseItem(); blank line adds {@code <br/><br/>}.
     *
     * @param start
     */
    private int parseListItem (int start) {
        start = skipSpacesToNewline(start, wikiLength); // skip spaces
        int end=parseItem(start, null, ContextType.LIST_ITEM);
        if ((listLevels[listLevel]=='>' || listLevels[listLevel]==':') &&
            wikiText.substring(start, end).trim().length()==0) { // empty line within blockquote/div
            if (!blockquoteBR) {
                sb.append("<br/><br/>");
                blockquoteBR=true;
            }
        }
        else {
            blockquoteBR=false;
        }
        return end;
    }

    /**
     * @param p points to first slash in suspected URI (scheme://etc)
     * @param start points to beginning of parsed item
     * @param end points to end of parsed item
     *
     * @return array of two integer offsets [begin_uri, end_uri] if matched, null otherwise
     */
    private int[] checkURI (int p, int start, int end) {
        if (p>start && wikiChars[p-1]==':') { // "://" found
            int pb=p-1;
            while (pb>start && isLatinLetterOrDigit(wikiChars[pb-1])) pb--;
            int pe=p+2;
            while (pe<end && isUrlChar(wikiChars[pe])) pe++;
            String uri = null;
            do {
                // don't want these chars at the end of URI
                while (pe>p+2 && ",.;:?!%)".indexOf(wikiChars[pe-1])>=0) pe--;
                if (isURI(wikiText.substring(pb, pe))) {
                    uri = wikiText.substring(pb, pe);
                } else {
                    pe--; // try chopping from the end
                }
            } while (uri==null && pe>p+2);
            if (uri!=null && isAbsoluteURI(uri)) {
                int offs[]= {pb, pe};
                return offs;
            }
        }
        return null;
    }

    private int parseItem (int start, String delimiter, ContextType context) {
        try {
            return parseItemThrow(start, delimiter, context);
        } catch (EndOfContextException e) {
            return e.position;
        }
    }

    private int parseItemThrow (int start, String delimiter, ContextType context)
        throws EndOfContextException {
        StringBuilder tb=new StringBuilder();

        boolean specialCaseDelimiterHandling="//".equals(delimiter);
        int p=start;
        int end=wikiLength;

        try {
          nextChar:
            while(true) {
                if (p>=end) throw new EndOfContextException(end); //break;

                if (delimiter!=null && wikiText.startsWith(delimiter, p)) {
                    if (!specialCaseDelimiterHandling || checkURI(p, start, end)==null) {
                        p+=delimiter.length();
                        return p;
                    }
                }

                char c=wikiChars[p];
                boolean atLineStart=false;

                // context-defined break test
                if (c=='\n') {
                    if (context==ContextType.HEADER || context==ContextType.TABLE_CELL) {
                        p++;
                        throw new EndOfContextException(p);
                    }
                    if (p+1<end && wikiChars[p+1]=='\n') { // blank line delimits everything
                        p++; // eat one '\n' and leave another one unparsed so parseBlock()
                             // can close all lists
                        throw new EndOfContextException(p);
                    }
                    p = skipSpacesToNewline(p+1, end); // skip whitespace
                    if (p>=end) throw new EndOfContextException(p); // end of text reached

                    c=wikiChars[p];
                    atLineStart=true;

                    if (c=='-' && wikiText.startsWith("----", p)) { // check for ---- <hr>
                        int pp = skipSpacesTabs(p+4, end); // skip spaces
                        // yes, it's <hr>
                        if (pp==end || wikiChars[pp]=='\n') throw new EndOfContextException(p);
                    }

                    if (LIST_CHARS.indexOf(c)>=0) { // start of list item?
                        if (FORMAT_CHARS.indexOf(c)<0) throw new EndOfContextException(p);
                        // here we have a list char, which also happen to be a format char
                        if (p+1<end && wikiChars[p+1]!=c) // format chars go in pairs
                            throw new EndOfContextException(p);
                        if (/*context==ContextType.LIST_ITEM*/ listLevel>=0 && c==listLevels[0]) {
                            // c matches current list's first level, so it must be new list item
                            throw new EndOfContextException(p);
                        }
                        // otherwise it must be just formatting sequence => no break of context
                    }
                    else if (c=='=') { // header
                        throw new EndOfContextException(p);
                    }
                    else if (c=='|') { // table or mediawiki-table
                        throw new EndOfContextException(p);
                    }
                    else if (c=='{') { // mediawiki-table?
                        if (p+1<end && wikiChars[p+1]=='|') {
                            int pp = skipSpacesTabs(p+2, end); // skip spaces
                            if (pp==end || wikiChars[pp]=='\n') // yes, it's start of a table
                                throw new EndOfContextException(p);
                        }
                    }

                    // if none matched add '\n' to text buffer
                    tb.append('\n');
                    // p and c already shifted past the '\n' and whitespace after, so go on
                }
                else if (c=='|') {
                    if (context==ContextType.TABLE_CELL) {
                        p++;
                        throw new EndOfSubContextException(p);
                    }
                }

                int formatType;

                if (c=='{') {
                    if (p+1<end && wikiChars[p+1]=='{') {
                        if (p+2<end && wikiChars[p+2]=='{') { // inline or block <nowiki>
                            flushToText(tb); // flush text buffer
                            int startNowiki=p+3;
                            int endNowiki=findEndOfNowiki(startNowiki);
                            p=endNowiki+3;
                            if (wikiText.lastIndexOf('\n', endNowiki)>=startNowiki) { // block <pre>
                                // skip the very first '\n'
                                if (wikiChars[startNowiki]=='\n') startNowiki++;
                                // omit the very last '\n'
                                if (wikiChars[endNowiki-1]=='\n') endNowiki--;
                                // break the paragraph because XHTML does not allow <pre> children
                                // of <p>
                                if (context==ContextType.PARAGRAPH) sb.append("</p>");
                                sb.append("<pre>");
                                appendNowiki(wikiText.substring(startNowiki, endNowiki));
                                sb.append("</pre>\n");
                                // continue the paragraph
                                if (context==ContextType.PARAGRAPH) sb.append("<p>");
                                // in this context return immediately after nowiki
                                //if (context==ContextType.NOWIKI_BLOCK) return p;
                            }
                            else { // inline <nowiki>
                                appendNowiki(wikiText.substring(startNowiki, endNowiki));
                            }
                            continue;
                        }
                        else if (p+2<end) { // {{image}}
                            int endImg=wikiText.indexOf("}}", p+2);
                            if (endImg>=0 && endImg<end) {
                                flushToText(tb); // flush text buffer
                                appendImage(wikiText.substring(p+2, endImg));
                                p=endImg+2;
                                continue;
                            }
                        }
                    }
                }
                else if (c=='[') {
                    if (p+1<end && wikiChars[p+1]=='[') { // [[link]]
                        int endLink=wikiText.indexOf("]]", p+2);
                        if (endLink>=0 && endLink<end) {
                            // flush text buffer
                            flushToText(tb);
                            appendLink(wikiText.substring(p+2, endLink));
                            p=endLink+2;
                            continue;
                        }
                    }
                }
                else if (c=='`') {
                    int endCode=wikiText.indexOf("`", p+1); // `inline code`
                    if (endCode>=0 && endCode<end) {
                        flushToText(tb); // flush text buffer
                        sb.append("<code>");
                        sb.append(escapeHTML(wikiText.substring(p+1, endCode)));
                        sb.append("</code>");
                        p=endCode+1;
                        continue;
                    }
                }
                else if (c=='\\') {
                    if (p+1<end && wikiChars[p+1]=='\\') { // \\ = <br/>
                        flushToText(tb); // flush text buffer
                        sb.append("<br/>");
                        p+=2;
                        continue;
                    }
                }
                // else if (c=='<') {
                //     if (p+1<end && wikiChars[p+1]=='<') {
                //         if (p+2<end && wikiChars[p+2]=='<') { // <<<macro>>>
                //             int endMacro=wikiText.indexOf(">>>", p+3);
                //             if (endMacro>=0 && endMacro<end) {
                //                 // flush text buffer
                //                 flushToText(tb);
                //                 appendMacro(wikiText.substring(p+3, endMacro));
                //                 p=endMacro+3;
                //                 continue;
                //             }
                //         }
                //     }
                // }
                else if ((formatType=FORMAT_CHARS.indexOf(c))>=0) {
                    if (p+1 < end && wikiChars[p+1] == c &&
                        // make sure we see a matching close delimiter somewhere ahead
                        wikiText.substring(p+2, end).indexOf(""+c+c) != -1) {
                        flushToText(tb); // flush text buffer
                        if (c=='/') {
                            // special case for "//" - check if it is part of URL (scheme://etc)
                            int[] uriOffs=checkURI(p, start, end);
                            if (uriOffs!=null) {
                                int pb=uriOffs[0], pe=uriOffs[1];
                                if (pb>start && wikiChars[pb-1]=='~') {
                                    // roll back URL + tilde
                                    sb.delete(sb.length()-(p-pb+1), sb.length());
                                    sb.append(escapeHTML(wikiText.substring(pb, pe)));
                                }
                                else {
                                    sb.delete(sb.length()-(p-pb), sb.length()); // roll back URL
                                    appendLink(wikiText.substring(pb, pe));
                                }
                                p=pe;
                                continue;
                            }
                        }
                        sb.append(FORMAT_TAG_OPEN[formatType]);
                        try {
                            p=parseItemThrow(p+2, FORMAT_DELIM[formatType], context);
                        }
                        finally {
                            sb.append(FORMAT_TAG_CLOSE[formatType]);
                        }
                        continue;
                    }
                    else if (c=='-') { // ' -- ' => &mdash;
                        if (p+2 < end && wikiChars[p+1] == '-' && wikiChars[p+2] == ' ' &&
                            p > start && wikiChars[p-1] == ' ') {
                            tb.append("&mdash; ");
                            p+=3;
                            continue;
                        }
                    }
                }
                else if (c=='~') { // escape
                    // most start line escapes are dealt with in parseBlock()
                    if (atLineStart) {
                        // same as block-level escaping: '*' '-' '#' '>' ':' '|' '='
                        if (p+1<end) {
                            char nc=wikiChars[p+1];
                            if (nc=='>' || nc==':' || nc=='-' || nc=='|' || nc=='=' || nc=='!') {
                                // can't be inline markup
                                tb.append(nc);
                                p+=2; // skip '~' and nc
                                continue nextChar;
                            }
                            else if (nc=='*' || nc=='#') {
                                // might be inline markup so need to double check
                                char nnc=p+2<end? wikiChars[p+2]:0;
                                if (nnc!=nc) {
                                    tb.append(nc);
                                    p+=2; // skip '~' and nc
                                    continue nextChar;
                                }
                                // otherwise escaping will be done at line level
                            }
                            else if (nc=='{') {
                                // might be inline {{{ markup so need to double check
                                char nnc=p+2<end? wikiChars[p+2]:0;
                                if (nnc=='|') { // mediawiki-table?
                                    tb.append(nc);
                                    tb.append(nnc);
                                    p+=3; // skip '~', nc and nnc
                                    continue nextChar;
                                }
                                // otherwise escaping will be done as usual at line level
                            }
                        }
                    }
                    for (String e: ESCAPED_INLINE_SEQUENCES) {
                        if (wikiText.startsWith(e, p+1)) {
                            tb.append(e);
                            p+=1+e.length();
                            continue nextChar;
                        }
                    }
                }
                tb.append(c);
                p++;
            }
        } finally {
            flushToText(tb);
        }
    }

    private void flushToText (StringBuilder flush) {
        appendText(flush.toString()); flush.delete(0, flush.length());
    }

    private static class EndOfContextException extends Exception {
        private static final long serialVersionUID=1L;
        int position;
        public EndOfContextException (int position) {
            super();
            this.position=position;
        }
    }

    private static class EndOfSubContextException extends EndOfContextException {
        private static final long serialVersionUID=1L;
        public EndOfSubContextException (int position) {
            super(position);
        }
    }

    protected StringBuilder sb=new StringBuilder();
    protected StringBuilder toc=new StringBuilder();
    protected int tocLevel=0;

    protected int HEADING_LEVEL_SHIFT=1; // make =h2, ==h3, ...
    protected String HEADING_ID_PREFIX=null;

    private int wikiLength;
    private char wikiChars[];
    private HashSet<String> tocAnchorIds=new HashSet<String>();
    private String wikiText;
    private int pos=0;
    private int listLevel=-1;
    private static final int MAX_LIST_LEVELS=100;
    private char listLevels[]=new char[MAX_LIST_LEVELS+1]; // max number of levels allowed
    private boolean blockquoteBR=false;
    private boolean inTable=false;
    private int mediawikiTableLevel=0;

    private static enum ContextType {PARAGRAPH, LIST_ITEM, TABLE_CELL, HEADER, NOWIKI_BLOCK};

    private static final String[] ESCAPED_INLINE_SEQUENCES= {
        "{{{", "{{", "}}}", "**", "//", "__", "##", "\\\\", "[[", "<<<", "~", "--", "|"};

    private static final String LIST_CHARS="*-#>:!";
    private static final String[] LIST_OPEN= {
        "<ul><li>", "<ul><li>", "<ol><li>", "<blockquote>", "<div class='indent'>",
        "<div class='center'>"};
    private static final String[] LIST_CLOSE= {
        "</li></ul>\n", "</li></ul>\n", "</li></ol>\n", "</blockquote>\n", "</div>\n", "</div>\n"};

    private static final String FORMAT_CHARS="*/_#-";
    private static final String[] FORMAT_DELIM= {"**", "//", "__", "##", "--"};
    private static final String[] FORMAT_TAG_OPEN= {
        "<strong>", "<em>", "<span class=\"underline\">", "<tt>", "<strike>"};
    private static final String[] FORMAT_TAG_CLOSE= {
        "</strong>", "</em>", "</span>", "</tt>", "</strike>"};
}
