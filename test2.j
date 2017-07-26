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
  ldc 5
  anewarray java/lang/String
  astore 1
  aload 1
  ldc 4
  ldc "azarias"
  aastore
  aload 1
  ldc 4
  aaload
  invokestatic sal/Library/print(Ljava.lang.String;)V
    return
.limit locals 2
.end method

