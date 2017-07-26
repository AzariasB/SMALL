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
  aload 0
  ldc 0
  aaload
  astore 1
  aload 1
  invokestatic sal/Library/print(Ljava.lang.String;)V
  aload_0
  ldc 0
  ldc "coucou"
  aastore
  aload 0
  ldc 0
  aaload
  invokestatic sal/Library/print(Ljava.lang.String;)V
    return
.limit locals 2
.end method

