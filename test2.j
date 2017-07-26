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
  ldc "bonjour"
  astore 1
  ldc 1
  istore 2
  aload 1
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  iload 2
  invokestatic sal/Library/print(I)V
  ldc "\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
    return
.limit locals 3
.end method

