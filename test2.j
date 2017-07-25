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
  ldc 0
  ldc 1
  if_icmpeq TRUE_VAL#0
  iconst_0
  goto FALSE_VAL#1
TRUE_VAL#0:
  iconst_1
FALSE_VAL#1:
  ifeq FALSE_LABEL#3
  ldc "deux"
  goto END_QUERY#2
FALSE_LABEL#3:
  ldc "non"
END_QUERY#2:
  astore 1
  aload 1
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "deux"
  astore 2
  aload 2
  ldc "deux"
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
  ldc "coucou\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto EXIT_SWITCH#4
  goto AFTER_CASE#9
NEXT_CASE#6:
  aload 2
  ldc "trois"
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
  ldc "nope\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#13
NEXT_CASE#10:
  aload 2
  ldc "quatre"
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
  ldc "c'est bon\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#17
NEXT_CASE#14:
  aload 2
  ldc "vingt"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#19
  iconst_0
  goto FALSE_VAL#20
TRUE_VAL#19:
  iconst_1
FALSE_VAL#20:
  ifeq NEXT_CASE#18
AFTER_CASE#17:
  ldc "c'est y \n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto AFTER_CASE#21
NEXT_CASE#18:
AFTER_CASE#21:
  ldc "defaut\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
EXIT_SWITCH#4:
  ldc "Full loop\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc 0
  istore 3
NEXT_LOOP#22:
  iload 3
  ldc 10
  if_icmplt TRUE_VAL#24
  iconst_0
  goto FALSE_VAL#25
TRUE_VAL#24:
  iconst_1
FALSE_VAL#25:
  ifeq EXIT_LOOP#23
  iload 3
  ldc "\n"
  swap
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  swap
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  iinc 3  1
  goto NEXT_LOOP#22
EXIT_LOOP#23:
  ldc "Contionnal loop\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc 0
  istore 4
NEXT_LOOP#26:
  iload 4
  ldc "\n"
  swap
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  swap
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  iinc 4  1
  iload 4
  ldc 10
  if_icmpge TRUE_VAL#30
  iconst_0
  goto FALSE_VAL#31
TRUE_VAL#30:
  iconst_1
FALSE_VAL#31:
  ifeq NEXT_TEST#29
  goto EXIT_LOOP#27
  goto END_IF#28
NEXT_TEST#29:
END_IF#28:
  goto NEXT_LOOP#26
EXIT_LOOP#27:
  ldc "The loop\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc 0
  istore 5
NEXT_LOOP#32:
  iload 5
  ldc 2
  imul
  ldc "\n"
  swap
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  swap
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  iload 5
  ldc 100
  if_icmpgt TRUE_VAL#36
  iconst_0
  goto FALSE_VAL#37
TRUE_VAL#36:
  iconst_1
FALSE_VAL#37:
  ifeq NEXT_TEST#35
  goto EXIT_LOOP#33
  goto END_IF#34
NEXT_TEST#35:
END_IF#34:
  iinc 5  1
  goto NEXT_LOOP#32
EXIT_LOOP#33:
    return
.limit locals 6
.end method

