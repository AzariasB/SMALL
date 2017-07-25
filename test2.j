.class public test2

.super java/lang/Object
.method public <init>()V
.limit stack 10
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method
.method public static main([Ljava/lang/String;)V
.limit stack 10
  ldc "10"
  invokestatic sal/Library/toInt(Ljava.lang.String;)I
  istore 1
  iload 1
  invokestatic sal/Library/print(I)V
    return
.limit locals 2
.end method

