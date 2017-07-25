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
  ldc "cent"
  astore 1
  aload 1
  ldc "deux"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#2
  iconst_0
  goto FALSE_VAL#3
TRUE_VAL#2:
  iconst_1
FALSE_VAL#3:
  ifeq NEXT_CASE#1
  ldc "coucou\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#0
NEXT_CASE#1:
  aload 1
  ldc "trois"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#5
  iconst_0
  goto FALSE_VAL#6
TRUE_VAL#5:
  iconst_1
FALSE_VAL#6:
  ifeq NEXT_CASE#4
  ldc "nope\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#0
NEXT_CASE#4:
  aload 1
  ldc "quatre"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#8
  iconst_0
  goto FALSE_VAL#9
TRUE_VAL#8:
  iconst_1
FALSE_VAL#9:
  ifeq NEXT_CASE#7
  ldc "c'est bon\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#0
NEXT_CASE#7:
  aload 1
  ldc "vingt"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#11
  iconst_0
  goto FALSE_VAL#12
TRUE_VAL#11:
  iconst_1
FALSE_VAL#12:
  ifeq NEXT_CASE#10
  ldc "c'est y \n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#0
  goto EXIT_SWITCH#0
NEXT_CASE#10:
  ldc "defaut\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
EXIT_SWITCH#0:
    return
.limit locals 2
.end method

