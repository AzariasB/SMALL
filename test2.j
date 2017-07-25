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
  ldc "deux"
  astore 1
  aload 1
  ldc "deux"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#3
  iconst_0
  goto FALSE_VAL#4
TRUE_VAL#3:
  iconst_1
FALSE_VAL#4:
  ifeq NEXT_CASE#2
AFTER_CASE#1:
  ldc "coucou\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#0
  goto AFTER_CASE#5
NEXT_CASE#2:
  aload 1
  ldc "trois"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#7
  iconst_0
  goto FALSE_VAL#8
TRUE_VAL#7:
  iconst_1
FALSE_VAL#8:
  ifeq NEXT_CASE#6
AFTER_CASE#5:
  ldc "nope\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#9
NEXT_CASE#6:
  aload 1
  ldc "quatre"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#11
  iconst_0
  goto FALSE_VAL#12
TRUE_VAL#11:
  iconst_1
FALSE_VAL#12:
  ifeq NEXT_CASE#10
AFTER_CASE#9:
  ldc "c'est bon\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#13
NEXT_CASE#10:
  aload 1
  ldc "vingt"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#15
  iconst_0
  goto FALSE_VAL#16
TRUE_VAL#15:
  iconst_1
FALSE_VAL#16:
  ifeq NEXT_CASE#14
AFTER_CASE#13:
  ldc "c'est y \n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#17
NEXT_CASE#14:
AFTER_CASE#17:
  ldc "defaut\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
EXIT_SWITCH#0:
    return
.limit locals 2
.end method

