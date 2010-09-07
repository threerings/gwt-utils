//
// $Id$

package com.threerings.gwt.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.threerings.gwt.util.MessagesLookup;

/**
 * Implementation generator for classes extending MessagesLookup.
 */
public class MessagesLookupGenerator extends Generator
{
    @Override
    public String generate (TreeLogger logger, GeneratorContext ctx, String typeName)
        throws UnableToCompleteException
    {
        _typeName = typeName;

        try {
            TypeOracle oracle = ctx.getTypeOracle();
            JClassType classType = oracle.getType(_typeName);

            _using = oracle.getType(_typeName).getAnnotation(MessagesLookup.Lookup.class).using();
            _packageName = classType.getPackage().getName();
            _className = classType.getSimpleSourceName() + "Impl";

            generateClass(logger, ctx);

        } catch (Exception e) {
            logger.log(TreeLogger.ERROR, e.getMessage(), e);
            throw new UnableToCompleteException();
        }

        return _packageName + "." + _className;
    }

    protected void generateClass (TreeLogger logger, GeneratorContext ctx)
        throws NotFoundException
    {
        PrintWriter printWriter = ctx.tryCreate(logger, _packageName, _className);
        if (printWriter == null) {
            // if this is null, the class was already generated for another module so we don't need
            // to generate it again
            return;
        }

        ClassSourceFileComposerFactory composer =
            new ClassSourceFileComposerFactory(_packageName, _className);
        composer.setSuperclass(_typeName);

        SourceWriter code = composer.createSourceWriter(ctx, printWriter);
        code.println("protected static final " + _using +
                     " msg = com.google.gwt.core.client.GWT.create(" + _using + ".class);");

        code.println("private " + _className + "() { }"); // TODO: omit?
        generateLookupMethod(ctx, code);

        // close generated class
        code.outdent();
        code.println("}");

        // commit generated class
        ctx.commit(logger, printWriter);
    }

    protected void generateLookupMethod (GeneratorContext ctx, SourceWriter code)
        throws NotFoundException
    {
        JMethod[] methods = ctx.getTypeOracle().getType(_using).getMethods();
        code.println("@Override public String fetch (String key, Object... params) {");
        code.indent();
        code.println("int length = (params != null) ? params.length : 0;");
        for (JMethod method : methods) {
            String s = method.getName();
            int jpi = method.getParameters().length;
            code.println("if (key.equals(\"" + s + "\") && length >= " + jpi + ") {");
            code.indent();
            code.print("return msg." + s + "(");
            for (int jj = 0; jj < jpi; jj++) {
                if (jj > 0) {
                    code.print(", ");
                }
                code.print("params[" + jj + "].toString()");
            }
            code.println(");"); // end return
            code.outdent();
            code.println("}"); // end if
        }

        code.println("return \"Invalid key: \" + key + \" with \" + length + \" params.\";");

        // end function
        code.outdent();
        code.println("}");
    }

    protected String _typeName;
    protected String _using;

    protected String _packageName;
    protected String _className;
}
