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
  ldc "Simple loop\n"
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
  ldc "Incremental loop\n"
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
  ldc "Conditionnal loop\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc 0
  istore 6
NEXT_LOOP#38:
  iload 6
  ldc 10
  if_icmplt TRUE_VAL#40
  iconst_0
  goto FALSE_VAL#41
TRUE_VAL#40:
  iconst_1
FALSE_VAL#41:
  ifeq EXIT_LOOP#39
  iinc 6  1
  iload 6
  ldc 5
  if_icmpeq TRUE_VAL#44
  iconst_0
  goto FALSE_VAL#45
TRUE_VAL#44:
  iconst_1
FALSE_VAL#45:
  ifeq NEXT_TEST#43
  goto NEXT_LOOP#38
  goto END_IF#42
NEXT_TEST#43:
END_IF#42:
  ldc "Conditional "
  iload 6
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  goto NEXT_LOOP#38
EXIT_LOOP#39:
  ldc "hello"
  ldc 2
  invokestatic sal/Library/left(Ljava.lang.String;I)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "good bye"
  ldc 3
  invokestatic sal/Library/right(Ljava.lang.String;I)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "good"
  ldc "bad"
  invokestatic sal/Library/strCmp(Ljava.lang.String;Ljava.lang.String;)I
  iconst_0
  if_icmpeq TRUE_VAL#46
  iconst_0
  goto FALSE_VAL#47
TRUE_VAL#46:
  iconst_1
FALSE_VAL#47:
  ifeq FALSE_LABEL#49
  ldc "Good is bad\n"
  goto END_QUERY#48
FALSE_LABEL#49:
  ldc "Good is not bad\n"
END_QUERY#48:
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "Input:"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "3"
  astore 7
  ldc "Saisir une chaine: "
  invokestatic sal/Library/print(Ljava.lang.String;)V
  invokestatic sal/Library/readStr()Ljava.lang.String;
  astore 7
  ldc "Vous avez saisit : "
  aload 7
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  aload 7
  ldc "'s length : "
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  aload 7
  invokestatic sal/Library/len(Ljava.lang.String;)I
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "Input a number:"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  invokestatic sal/Library/readStr()Ljava.lang.String;
  astore 8
  ldc "Input + 10 = "
  aload 8
  invokestatic sal/Library/toInt(Ljava.lang.String;)I
  ldc 10
  iadd
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "bonjour"
  astore 9
  ldc 1
  istore 10
  aload 9
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc "\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  iload 10
  invokestatic sal/Library/print(I)V
  ldc "\n"
  invokestatic sal/Library/print(Ljava.lang.String;)V
  ldc 5
  newarray int
  astore 11
  aload 11
  ldc 4
  ldc 6
  iastore
  ldc "Longueur de l'array : "
  aload 11
  arraylength
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
  aload 11
  ldc 4
  iaload
  ldc "\n"
  swap
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  swap
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "coucou"
  invokestatic sal/Library/len(Ljava.lang.String;)I
  invokestatic sal/Library/toStr(I)Ljava.lang.String;
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  ldc "\n"
  invokestatic sal/Library/concat(Ljava.lang.String;Ljava.lang.String;)Ljava.lang.String;
  invokestatic sal/Library/print(Ljava.lang.String;)V
    return
.limit locals 12
.end method

