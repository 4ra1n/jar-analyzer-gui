package me.n1ar4.jar.analyzer.asm;

import me.n1ar4.jar.analyzer.core.MethodReference;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.HashSet;

public class MethodCallClassVisitor extends ClassVisitor {

    private String name;

    private final HashMap<MethodReference.Handle, HashSet<MethodReference.Handle>> methodCalls;

    public MethodCallClassVisitor(HashMap<MethodReference.Handle,
            HashSet<MethodReference.Handle>> methodCalls) {
        super(Opcodes.ASM9);
        this.methodCalls = methodCalls;
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.name = name;
    }

    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new MethodCallMethodVisitor(api, mv, this.name, name, desc, methodCalls);
    }

}
