# JAVA字节码文件分析

## 案例1 HelloByteCode

### 源代码

HelloByteCode.java

```java
package com.xianyanyang.jvm.bytecode;
public class HelloByteCode {
    public static void main(String[] args) {
        HelloByteCode obj = new HelloByteCode();
    }
}
```

### 字节码文件

执行以下java命令生成字节码文件HelloByteCode.class：

```
javac -g HelloByteCode.java
```

```
cafe babe 0000 0034 0016 0a00 0400 1307
0014 0a00 0200 1307 0015 0100 063c 696e
6974 3e01 0003 2829 5601 0004 436f 6465
0100 0f4c 696e 654e 756d 6265 7254 6162
6c65 0100 124c 6f63 616c 5661 7269 6162
6c65 5461 626c 6501 0004 7468 6973 0100
2c4c 636f 6d2f 7869 616e 7961 6e79 616e
672f 6a76 6d2f 6279 7465 636f 6465 2f48
656c 6c6f 4279 7465 436f 6465 3b01 0004
6d61 696e 0100 1628 5b4c 6a61 7661 2f6c
616e 672f 5374 7269 6e67 3b29 5601 0004
6172 6773 0100 135b 4c6a 6176 612f 6c61
6e67 2f53 7472 696e 673b 0100 036f 626a
0100 0a53 6f75 7263 6546 696c 6501 0012
4865 6c6c 6f42 7974 6543 6f64 652e 6a61
7661 0c00 0500 0601 002a 636f 6d2f 7869
616e 7961 6e79 616e 672f 6a76 6d2f 6279
7465 636f 6465 2f48 656c 6c6f 4279 7465
436f 6465 0100 106a 6176 612f 6c61 6e67
2f4f 626a 6563 7400 2100 0200 0400 0000
0000 0200 0100 0500 0600 0100 0700 0000
2f00 0100 0100 0000 052a b700 01b1 0000
0002 0008 0000 0006 0001 0000 0003 0009
0000 000c 0001 0000 0005 000a 000b 0000
0009 000c 000d 0001 0007 0000 0041 0002
0002 0000 0009 bb00 0259 b700 034c b100
0000 0200 0800 0000 0a00 0200 0000 0600
0800 0700 0900 0000 1600 0200 0000 0900
0e00 0f00 0000 0800 0100 1000 0b00 0100
0100 1100 0000 0200 12
```

> 文件开头的4个字节"cafe babe"称之为魔数，只有以"cafe babe"开头的class文件才可以被JVM接受，这四个字节就是字节码文件的身份识别。

> 字节码文件内容为十六进制；一个操作码为1Byte=8bit，一个十六进制数字=4个bit=(1/2) Byte，一个操作码=2个十六进制数 ，如上图红色标记 bb为一个操作码，映射指令为 new；

### 字节码注记符文件

```
javap -c -verbose HelloByteCode.class > HelloByteCode.txt
```

```ASN.1
Classfile /E:/.../week1/src/main/java/com/xianyanyang/jvm/bytecode/HelloByteCode.class
  Last modified 2021-3-2; size 473 bytes
  MD5 checksum f136062421deb48ad26d540039f82d47
  Compiled from "HelloByteCode.java"
public class com.xianyanyang.jvm.bytecode.HelloByteCode
  minor version: 0    --- 次版本号
  major version: 52   --- 主版本号，52即java 8 ,51对应java 7
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
--- Constant pool，常量池可以理解为Class中的资源仓库。
--- 主要存放两大常量：
--- 1）字面量，类似于Java中的常量概念，包括文本字符串，final常量等。
--- 2）符号引用，包括：类和接口的全限定名、字段的名称和描述符号、方法的名称和描述符

   #1 = Methodref          #4.#19         // java/lang/Object."<init>":()V
--- 第一个常量是一个方法定义，指向了第4个和第13个常量。
    --- 由此类推查看第4个和第13个常量，最后拼接成一个常量右侧的注释内容：“java/lang/Object."<init>":()V”
    --- 这段可以理解为该类的构造器声明，由于该类没有重写构造说明，所以调用的是父类的构造方法。
    --- 此处说明该类的父类是Object，该方法的默认返回值是V，也就是void，无返回值。

   #2 = Class              #20            // com/xianyanyang/jvm/bytecode/HelloByteCode
   #3 = Methodref          #2.#19         // com/xianyanyang/jvm/bytecode/HelloByteCode."<init>":()V
   #4 = Class              #21            // java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Utf8               Code
   #8 = Utf8               LineNumberTable
   #9 = Utf8               LocalVariableTable
  #10 = Utf8               this
  #11 = Utf8               Lcom/xianyanyang/jvm/bytecode/HelloByteCode;
  #12 = Utf8               main
  #13 = Utf8               ([Ljava/lang/String;)V
  #14 = Utf8               args
  #15 = Utf8               [Ljava/lang/String;
  #16 = Utf8               obj
  #17 = Utf8               SourceFile
  #18 = Utf8               HelloByteCode.java
  #19 = NameAndType        #5:#6          // "<init>":()V
  #20 = Utf8               com/xianyanyang/jvm/bytecode/HelloByteCode
  #21 = Utf8               java/lang/Object
{
  --- 构造函数
  public com.xianyanyang.jvm.bytecode.HelloByteCode();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0           --- 将第一个引用类型本地变量压栈
         1: invokespecial #1  // Method java/lang/Object."<init>":()V ---调用（#1）Object构造方法，实例初始化方法
         4: return            --- 从当前方法返回void
      
      LineNumberTable: --- 存放方法的行号信息。
      --- 源代码行数:字节码行数 
        line 3: 0
        
      LocalVariableTable: --- 方法本地变量表，描述了栈帧中局部变量的信息。
      --- Start表示该局部变量在哪一行开始可见；
          Length表示可见行数，也就是该变量的作用范围；
          Slot代表所在栈帧位置；
          Name是变量名称；
          Signature是类型签名；
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/xianyanyang/jvm/bytecode/HelloByteCode;
            
  --- main函数
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=1
         0: new           #2   // class com/xianyanyang/jvm/bytecode/HelloByteCode --- new HelloByteCode ()
         3: dup                -- 复制栈顶数值并将复制值压入栈顶
         4: invokespecial #3   // Method "<init>":()V   --- 调用（#3）HelloByteCode构造方法，实例初始化方法
         7: astore_1           -- 将栈顶引用型数值存入本地变量orj
         8: return             -- 从当前方法返回void
      LineNumberTable:
        line 6: 0
        line 7: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
            8       1     1   obj   Lcom/xianyanyang/jvm/bytecode/HelloByteCode;
}
SourceFile: "HelloByteCode.java"
```

## 案例2 四则运算类Math

### 源代码

```java
package com.xianyanyang.jvm.bytecode;

public class Math {

    public void foo() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 5;
    }
}
```

### 字节码注记符文件

```ASN.1
Classfile /E:/JAVA-01/...week1/src/main/java/com/xianyanyang/jvm/bytecode/Math.class
  Last modified 2021-3-2; size 426 bytes
  MD5 checksum 44f9ad9392429e85ad1895169810dbad
  Compiled from "Math.java"
public class com.xianyanyang.jvm.bytecode.Math
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#18         // java/lang/Object."<init>":()V
   #2 = Class              #19            // com/xianyanyang/jvm/bytecode/Math
   #3 = Class              #20            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Lcom/xianyanyang/jvm/bytecode/Math;
  #11 = Utf8               foo
  #12 = Utf8               a
  #13 = Utf8               I
  #14 = Utf8               b
  #15 = Utf8               c
  #16 = Utf8               SourceFile
  #17 = Utf8               Math.java
  #18 = NameAndType        #4:#5          // "<init>":()V
  #19 = Utf8               com/xianyanyang/jvm/bytecode/Math
  #20 = Utf8               java/lang/Object
{
  public com.xianyanyang.jvm.bytecode.Math();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/xianyanyang/jvm/bytecode/Math;

  --  public void foo() {
  --      int a = 1;
  --      int b = 2;
  --      int c = (a + b) * 5;
  --  }
    
  public void foo();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=4, args_size=1
         0: iconst_1   --  将int型1压栈
         1: istore_1   --  将栈顶int型数值存入本地变量 a
         2: iconst_2   --  将int型2压栈
         3: istore_2   --  将栈顶int型数值存入本地变量 b
         4: iload_1    --  将int型本地变量 a 压栈
         5: iload_2    --  将int型本地变量 b 压栈
         6: iadd       --  将栈顶a和b两int型数值相加并将结果压栈
         7: iconst_5   --  将int型5压栈
         8: imul       --  将栈顶两int型数值相乘并将结果压入栈顶
         9: istore_3   --  将栈顶int型数值存入本地变量c
        10: return     --  从当前方法返回void
      LineNumberTable:
        line 6: 0
        line 7: 2
        line 8: 4
        line 9: 10
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      11     0  this   Lcom/xianyanyang/jvm/bytecode/Math;
            2       9     1     a   I
            4       7     2     b   I
           10       1     3     c   I
}
SourceFile: "Math.java"
```

## 案例三 循环Loop

### 源代码

```java
package com.xianyanyang.jvm.bytecode;

public class Loop {
    private int result = 0;

    public void loop(int[] numbers) {
        for (int number : numbers) {
            result += number;
        }
    }
}
```

### 编译后的文件

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xianyanyang.jvm.bytecode;

public class Loop {
    private int result = 0;

    public Loop() {
    }

    public void loop(int[] ages) {
        int[] var2 = ages;
        int var3 = ages.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int age = var2[var4];
            this.result += age;
        }
    }
}

```

### 字节码注记符文件

```ASN.1
Classfile /E:/JAVA-01/Week_01/2021-02-28/week1/src/main/java/com/xianyanyang/jvm/bytecode/Loop.class
  Last modified 2021-3-2; size 532 bytes
  MD5 checksum 669ecc2bf8a1e49062a3eed1b3b1fb04
  Compiled from "Loop.java"
public class com.xianyanyang.jvm.bytecode.Loop
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#23         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#24         // com/xianyanyang/jvm/bytecode/Loop.result:I
   #3 = Class              #25            // com/xianyanyang/jvm/bytecode/Loop
   #4 = Class              #26            // java/lang/Object
   #5 = Utf8               result
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lcom/xianyanyang/jvm/bytecode/Loop;
  #14 = Utf8               loop
  #15 = Utf8               ([I)V
  #16 = Utf8               age
  #17 = Utf8               ages
  #18 = Utf8               [I
  #19 = Utf8               StackMapTable
  #20 = Class              #18            // "[I"
  #21 = Utf8               SourceFile
  #22 = Utf8               Loop.java
  #23 = NameAndType        #7:#8          // "<init>":()V
  #24 = NameAndType        #5:#6          // result:I
  #25 = Utf8               com/xianyanyang/jvm/bytecode/Loop
  #26 = Utf8               java/lang/Object
{
  public com.xianyanyang.jvm.bytecode.Loop();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iconst_0
         6: putfield      #2                  // Field result:I
         9: return
      LineNumberTable:
        line 3: 0
        line 4: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0  this   Lcom/xianyanyang/jvm/bytecode/Loop;

    -- public void loop(int[] ages) {
    --    int[] var2 = ages;
    --    int var3 = ages.length;
    --    for(int var4 = 0; var4 < var3; ++var4) {
    --        int age = var2[var4];
    --        this.result += age;
    --    }
    -- }
  public void loop(int[]);
    descriptor: ([I)V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=6, args_size=2
         0: aload_1         	-- 将Slot1引用类型本地变量推送至栈顶 ages 
         1: astore_2         	-- 将栈顶引用型数值存入Slot2本地变量 var2
         2: aload_2         	-- 将Slot2引用类型本地变量推送至栈顶 var2
         3: arraylength         -- 将数组从栈中弹出，获得数组的长度值并压入栈顶 var2.length
         4: istore_3         	-- 将栈顶int型数值存入Slot3本地变量 var3
         5: iconst_0         	-- 将int型0推送至栈顶
         6: istore        4         -- 将栈顶int型数值存入指定本地变量 var4
         8: iload         4         -- 将指定的int型本地变量推送至栈顶 var4
        10: iload_3         		-- 将Slot3的int型本地变量推送至栈顶 var3
        11: if_icmpge     37        -- 比较栈顶两int型数值var4和var3的大小，当结果大于等于0时跳转
        14: aload_2         		-- 将Slot2引用类型本地变量推送至栈顶 var2
        15: iload         4         -- 将指定的int型本地变量推送至栈顶 var4
        17: iaload         			-- 将int型数组指定索引的值推送至栈顶 var2[var4]
        18: istore        5         -- 将栈顶int型数值存入指定本地变量 age
        20: aload_0         		-- 将Slot0的引用类型本地变量推送至栈顶 this
        21: dup         			-- 复制栈顶数值并将复制值压入栈顶 this
        22: getfield      #2        -- 获取指定类的实例域，并将其值压入栈顶 this.result    // Field result:I
        25: iload         5         -- 将指定的int型本地变量推送至栈顶 age
        27: iadd         			-- 将栈顶两int型数值相加并将结果压入栈顶 this.result+age
        28: putfield      #2        -- 为指定的类的实例域赋值 		this.result			// Field result:I
        31: iinc          4, 1      -- 将指定int型变量增加指定值   var4+1
        34: goto          8         -- 无条件跳转
        37: return         			-- 从当前方法返回void
      LineNumberTable:
        line 7: 0
        line 8: 20
        line 7: 31
        line 10: 37
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           20      11     5   age   I
            0      38     0  this   Lcom/xianyanyang/jvm/bytecode/Loop;
            0      38     1  ages   [I
                        --2  var2
                        --3  var3
                        --4  var4
      StackMapTable: number_of_entries = 2
        frame_type = 254 /* append */
          offset_delta = 8
          locals = [ class "[I", int, int ]
        frame_type = 248 /* chop */
          offset_delta = 28
}
SourceFile: "Loop.java"
```

## Constant Pool常量池定义列表

| CONSTANT_Class_info | 描述                   |
| ------------------- | ---------------------- |
| utf8                | UTF-8编码的字符串      |
| integer             | 整型字面量             |
| Float               | 浮点数字面量           |
| Long                | 长整型字面量           |
| Double              | 双精度浮点型字面量     |
| Class               | 类或者接口的符号引用   |
| String              | 字符串类型字面量       |
| Fieldref            | 字段的符号引用         |
| Methodref           | 类中方法的符号引用     |
| InterfaceMethodref  | 接口中方法的符号引用   |
| NameAndType         | 字段或方法的符号引用   |
| MethodType          | 标志方法类型           |
| MethodHandle        | 表示方法句柄           |
| InvokeDynamic       | 表示一个动态方法调用点 |

> 字面量又称为直接量，是指在程序中通过源代码直接给出的值，例如在`int a = 5;`代码中，为变量 a 所分配的初始值 5 就是一个直字面量。

## 常用的JAVA字节码指令码注记符对应表

| 指令码 | 注记符          | 说明                                                         |
| ------ | --------------- | ------------------------------------------------------------ |
| 0x00   | nop             | 什么都不做                                                   |
| 0x01   | aconst_null     | 将null压栈                                                   |
| 0x02   | iconst_m1       | 将int型-1压栈                                                |
| 0x03   | iconst_0        | 将int型0压栈                                                 |
| 0x04   | iconst_1        | 将int型1压栈                                                 |
| 0x05   | iconst_2        | 将int型2压栈                                                 |
| 0x06   | iconst_3        | 将int型3压栈                                                 |
| 0x07   | iconst_4        | 将int型4压栈                                                 |
| 0x08   | iconst_5        | 将int型5压栈                                                 |
| 0x09   | lconst_0        | 将long型0压栈                                                |
| 0x0a   | lconst_1        | 将long型1压栈                                                |
| 0x0b   | fconst_0        | 将float型0压栈                                               |
| 0x0c   | fconst_1        | 将float型1压栈                                               |
| 0x0d   | fconst_2        | 将float型2压栈                                               |
| 0x0e   | dconst_0        | 将double型0压栈                                              |
| 0x0f   | dconst_1        | 将double型1压栈                                              |
| 0x10   | bipush          | 将单字节的常量值(-128~127)压栈                               |
| 0x11   | sipush          | 将一个短整型常量值(-32768~32767)压栈                         |
| 0x12   | ldc             | 将int, float或String型常量值从常量池中压栈                   |
| 0x13   | ldc_w           | 将int, float或String型常量值从常量池中压栈（宽索引）         |
| 0x14   | ldc2_w          | 将long或double型常量值从常量池中压栈（宽索引）               |
| 0x15   | iload           | 将指定的int型本地变量压栈                                    |
| 0x16   | lload           | 将指定的long型本地变量压栈                                   |
| 0x17   | fload           | 将指定的float型本地变量压栈                                  |
| 0x18   | dload           | 将指定的double型本地变量压栈                                 |
| 0x19   | aload           | 将指定的引用类型本地变量压栈                                 |
| 0x1a   | iload_0         | 将第一个int型本地变量压栈                                    |
| 0x1b   | iload_1         | 将第2个int型本地变量压栈                                     |
| 0x1c   | iload_2         | 将第三个int型本地变量压栈                                    |
| 0x1d   | iload_3         | 将第四个int型本地变量压栈                                    |
| 0x1e   | lload_0         | 将第一个long型本地变量压栈                                   |
| 0x1f   | lload_1         | 将第2个long型本地变量压栈                                    |
| 0x20   | lload_2         | 将第三个long型本地变量压栈                                   |
| 0x21   | lload_3         | 将第四个long型本地变量压栈                                   |
| 0x22   | fload_0         | 将第一个float型本地变量压栈                                  |
| 0x23   | fload_1         | 将第2个float型本地变量压栈                                   |
| 0x24   | fload_2         | 将第三个float型本地变量压栈                                  |
| 0x25   | fload_3         | 将第四个float型本地变量压栈                                  |
| 0x26   | dload_0         | 将第一个double型本地变量压栈                                 |
| 0x27   | dload_1         | 将第2个double型本地变量压栈                                  |
| 0x28   | dload_2         | 将第三个double型本地变量压栈                                 |
| 0x29   | dload_3         | 将第四个double型本地变量压栈                                 |
| 0x2a   | aload_0         | 将第一个引用类型本地变量压栈                                 |
| 0x2b   | aload_1         | 将第2个引用类型本地变量压栈                                  |
| 0x2c   | aload_2         | 将第三个引用类型本地变量压栈                                 |
| 0x2d   | aload_3         | 将第四个引用类型本地变量压栈                                 |
| 0x2e   | iaload          | 将int型数组指定索引的值压栈                                  |
| 0x2f   | laload          | 将long型数组指定索引的值压栈                                 |
| 0x30   | faload          | 将float型数组指定索引的值压栈                                |
| 0x31   | daload          | 将double型数组指定索引的值压栈                               |
| 0x32   | aaload          | 将引用型数组指定索引的值压栈                                 |
| 0x33   | baload          | 将boolean或byte型数组指定索引的值压栈                        |
| 0x34   | caload          | 将char型数组指定索引的值压栈                                 |
| 0x35   | saload          | 将short型数组指定索引的值压栈                                |
| 0x36   | istore          | 将栈顶int型数值存入指定本地变量                              |
| 0x37   | lstore          | 将栈顶long型数值存入指定本地变量                             |
| 0x38   | fstore          | 将栈顶float型数值存入指定本地变量                            |
| 0x39   | dstore          | 将栈顶double型数值存入指定本地变量                           |
| 0x3a   | astore          | 将栈顶引用型数值存入指定本地变量                             |
| 0x3b   | istore_0        | 将栈顶int型数值存入第一个本地变量                            |
| 0x3c   | istore_1        | 将栈顶int型数值存入第2个本地变量                             |
| 0x3d   | istore_2        | 将栈顶int型数值存入第三个本地变量                            |
| 0x3e   | istore_3        | 将栈顶int型数值存入第四个本地变量                            |
| 0x3f   | lstore_0        | 将栈顶long型数值存入第一个本地变量                           |
| 0x40   | lstore_1        | 将栈顶long型数值存入第2个本地变量                            |
| 0x41   | lstore_2        | 将栈顶long型数值存入第三个本地变量                           |
| 0x42   | lstore_3        | 将栈顶long型数值存入第四个本地变量                           |
| 0x43   | fstore_0        | 将栈顶float型数值存入第一个本地变量                          |
| 0x44   | fstore_1        | 将栈顶float型数值存入第2个本地变量                           |
| 0x45   | fstore_2        | 将栈顶float型数值存入第三个本地变量                          |
| 0x46   | fstore_3        | 将栈顶float型数值存入第四个本地变量                          |
| 0x47   | dstore_0        | 将栈顶double型数值存入第一个本地变量                         |
| 0x48   | dstore_1        | 将栈顶double型数值存入第2个本地变量                          |
| 0x49   | dstore_2        | 将栈顶double型数值存入第三个本地变量                         |
| 0x4a   | dstore_3        | 将栈顶double型数值存入第四个本地变量                         |
| 0x4b   | astore_0        | 将栈顶引用型数值存入第一个本地变量                           |
| 0x4c   | astore_1        | 将栈顶引用型数值存入第2个本地变量                            |
| 0x4d   | astore_2        | 将栈顶引用型数值存入第三个本地变量                           |
| 0x4e   | astore_3        | 将栈顶引用型数值存入第四个本地变量                           |
| 0x4f   | iastore         | 将栈顶int型数值存入指定数组的指定索引位置                    |
| 0x50   | lastore         | 将栈顶long型数值存入指定数组的指定索引位置                   |
| 0x51   | fastore         | 将栈顶float型数值存入指定数组的指定索引位置                  |
| 0x52   | dastore         | 将栈顶double型数值存入指定数组的指定索引位置                 |
| 0x53   | aastore         | 将栈顶引用型数值存入指定数组的指定索引位置                   |
| 0x54   | bastore         | 将栈顶boolean或byte型数值存入指定数组的指定索引位置          |
| 0x55   | castore         | 将栈顶char型数值存入指定数组的指定索引位置                   |
| 0x56   | sastore         | 将栈顶short型数值存入指定数组的指定索引位置                  |
| 0x57   | pop             | 将栈顶数值弹出 (数值不能是long或double类型的)                |
| 0x58   | pop2            | 将栈顶的一个（long或double类型的)或两个数值弹出（其它）      |
| 0x59   | dup             | 复制栈顶数值并将复制值压入栈顶                               |
| 0x5a   | dup_x1          | 复制栈顶数值并将两个复制值压入栈顶                           |
| 0x5b   | dup_x2          | 复制栈顶数值并将三个（或两个）复制值压入栈顶                 |
| 0x5c   | dup2            | 复制栈顶一个（long或double类型的)或两个（其它）数值并将复制值压入栈顶 |
| 0x5d   | dup2_x1         | <待补充>                                                     |
| 0x5e   | dup2_x2         | <待补充>                                                     |
| 0x5f   | swap            | 将栈最顶端的两个数值互换(数值不能是long或double类型的)       |
| 0x60   | iadd            | 将栈顶两int型数值相加并将结果压入栈顶                        |
| 0x61   | ladd            | 将栈顶两long型数值相加并将结果压入栈顶                       |
| 0x62   | fadd            | 将栈顶两float型数值相加并将结果压入栈顶                      |
| 0x63   | dadd            | 将栈顶两double型数值相加并将结果压入栈顶                     |
| 0x64   | isub            | 将栈顶两int型数值相减并将结果压入栈顶                        |
| 0x65   | lsub            | 将栈顶两long型数值相减并将结果压入栈顶                       |
| 0x66   | fsub            | 将栈顶两float型数值相减并将结果压入栈顶                      |
| 0x67   | dsub            | 将栈顶两double型数值相减并将结果压入栈顶                     |
| 0x68   | imul            | 将栈顶两int型数值相乘并将结果压入栈顶                        |
| 0x69   | lmul            | 将栈顶两long型数值相乘并将结果压入栈顶                       |
| 0x6a   | fmul            | 将栈顶两float型数值相乘并将结果压入栈顶                      |
| 0x6b   | dmul            | 将栈顶两double型数值相乘并将结果压入栈顶                     |
| 0x6c   | idiv            | 将栈顶两int型数值相除并将结果压入栈顶                        |
| 0x6d   | ldiv            | 将栈顶两long型数值相除并将结果压入栈顶                       |
| 0x6e   | fdiv            | 将栈顶两float型数值相除并将结果压入栈顶                      |
| 0x6f   | ddiv            | 将栈顶两double型数值相除并将结果压入栈顶                     |
| 0x70   | irem            | 将栈顶两int型数值作取模运算并将结果压入栈顶                  |
| 0x71   | lrem            | 将栈顶两long型数值作取模运算并将结果压入栈顶                 |
| 0x72   | frem            | 将栈顶两float型数值作取模运算并将结果压入栈顶                |
| 0x73   | drem            | 将栈顶两double型数值作取模运算并将结果压入栈顶               |
| 0x74   | ineg            | 将栈顶int型数值取负并将结果压入栈顶                          |
| 0x75   | lneg            | 将栈顶long型数值取负并将结果压入栈顶                         |
| 0x76   | fneg            | 将栈顶float型数值取负并将结果压入栈顶                        |
| 0x77   | dneg            | 将栈顶double型数值取负并将结果压入栈顶                       |
| 0x78   | ishl            | 将int型数值左移位指定位数并将结果压入栈顶                    |
| 0x79   | lshl            | 将long型数值左移位指定位数并将结果压入栈顶                   |
| 0x7a   | ishr            | 将int型数值右（符号）移位指定位数并将结果压入栈顶            |
| 0x7b   | lshr            | 将long型数值右（符号）移位指定位数并将结果压入栈顶           |
| 0x7c   | iushr           | 将int型数值右（无符号）移位指定位数并将结果压入栈顶          |
| 0x7d   | lushr           | 将long型数值右（无符号）移位指定位数并将结果压入栈顶         |
| 0x7e   | iand            | 将栈顶两int型数值作“按位与”并将结果压入栈顶                  |
| 0x7f   | land            | 将栈顶两long型数值作“按位与”并将结果压入栈顶                 |
| 0x80   | ior             | 将栈顶两int型数值作“按位或”并将结果压入栈顶                  |
| 0x81   | lor             | 将栈顶两long型数值作“按位或”并将结果压入栈顶                 |
| 0x82   | ixor            | 将栈顶两int型数值作“按位异或”并将结果压入栈顶                |
| 0x83   | lxor            | 将栈顶两long型数值作“按位异或”并将结果压入栈顶               |
| 0x84   | iinc            | 将指定int型变量增加指定值（i++, i--, i+=2）                  |
| 0x85   | i2l             | 将栈顶int型数值强制转换成long型数值并将结果压入栈顶          |
| 0x86   | i2f             | 将栈顶int型数值强制转换成float型数值并将结果压入栈顶         |
| 0x87   | i2d             | 将栈顶int型数值强制转换成double型数值并将结果压入栈顶        |
| 0x88   | l2i             | 将栈顶long型数值强制转换成int型数值并将结果压入栈顶          |
| 0x89   | l2f             | 将栈顶long型数值强制转换成float型数值并将结果压入栈顶        |
| 0x8a   | l2d             | 将栈顶long型数值强制转换成double型数值并将结果压入栈顶       |
| 0x8b   | f2i             | 将栈顶float型数值强制转换成int型数值并将结果压入栈顶         |
| 0x8c   | f2l             | 将栈顶float型数值强制转换成long型数值并将结果压入栈顶        |
| 0x8d   | f2d             | 将栈顶float型数值强制转换成double型数值并将结果压入栈顶      |
| 0x8e   | d2i             | 将栈顶double型数值强制转换成int型数值并将结果压入栈顶        |
| 0x8f   | d2l             | 将栈顶double型数值强制转换成long型数值并将结果压入栈顶       |
| 0x90   | d2f             | 将栈顶double型数值强制转换成float型数值并将结果压入栈顶      |
| 0x91   | i2b             | 将栈顶int型数值强制转换成byte型数值并将结果压入栈顶          |
| 0x92   | i2c             | 将栈顶int型数值强制转换成char型数值并将结果压入栈顶          |
| 0x93   | i2s             | 将栈顶int型数值强制转换成short型数值并将结果压入栈顶         |
| 0x94   | lcmp            | 比较栈顶两long型数值大小，并将结果（1，0，-1）压入栈顶       |
| 0x95   | fcmpl           | 比较栈顶两float型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为NaN时，将-1压入栈顶 |
| 0x96   | fcmpg           | 比较栈顶两float型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为NaN时，将1压入栈顶 |
| 0x97   | dcmpl           | 比较栈顶两double型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为NaN时，将-1压入栈顶 |
| 0x98   | dcmpg           | 比较栈顶两double型数值大小，并将结果（1，0，-1）压入栈顶；当其中一个数值为NaN时，将1压入栈顶 |
| 0x99   | ifeq            | 当栈顶int型数值等于0时跳转                                   |
| 0x9a   | ifne            | 当栈顶int型数值不等于0时跳转                                 |
| 0x9b   | iflt            | 当栈顶int型数值小于0时跳转                                   |
| 0x9c   | ifge            | 当栈顶int型数值大于等于0时跳转                               |
| 0x9d   | ifgt            | 当栈顶int型数值大于0时跳转                                   |
| 0x9e   | ifle            | 当栈顶int型数值小于等于0时跳转                               |
| 0x9f   | if_icmpeq       | 比较栈顶两int型数值大小，当结果等于0时跳转                   |
| 0xa0   | if_icmpne       | 比较栈顶两int型数值大小，当结果不等于0时跳转                 |
| 0xa1   | if_icmplt       | 比较栈顶两int型数值大小，当结果小于0时跳转                   |
| 0xa2   | if_icmpge       | 比较栈顶两int型数值大小，当结果大于等于0时跳转               |
| 0xa3   | if_icmpgt       | 比较栈顶两int型数值大小，当结果大于0时跳转                   |
| 0xa4   | if_icmple       | 比较栈顶两int型数值大小，当结果小于等于0时跳转               |
| 0xa5   | if_acmpeq       | 比较栈顶两引用型数值，当结果相等时跳转                       |
| 0xa6   | if_acmpne       | 比较栈顶两引用型数值，当结果不相等时跳转                     |
| 0xa7   | goto            | 无条件跳转                                                   |
| 0xa8   | jsr             | 跳转至指定16位offset位置，并将jsr下一条指令地址压入栈顶      |
| 0xa9   | ret             | 返回至本地变量指定的index的指令位置（一般与jsr, jsr_w联合使用） |
| 0xaa   | tableswitch     | 用于switch条件跳转，case值连续（可变长度指令）               |
| 0xab   | lookupswitch    | 用于switch条件跳转，case值不连续（可变长度指令）             |
| 0xac   | ireturn         | 从当前方法返回int                                            |
| 0xad   | lreturn         | 从当前方法返回long                                           |
| 0xae   | freturn         | 从当前方法返回float                                          |
| 0xaf   | dreturn         | 从当前方法返回double                                         |
| 0xb0   | areturn         | 从当前方法返回对象引用                                       |
| 0xb1   | return          | 从当前方法返回void                                           |
| 0xb2   | getstatic       | 获取指定类的静态域，并将其值压入栈顶                         |
| 0xb3   | putstatic       | 为指定的类的静态域赋值                                       |
| 0xb4   | getfield        | 获取指定类的实例域，并将其值压入栈顶                         |
| 0xb5   | putfield        | 为指定的类的实例域赋值                                       |
| 0xb6   | invokevirtual   | 调用实例方法                                                 |
| 0xb7   | invokespecial   | 调用超类构造方法，实例初始化方法，私有方法                   |
| 0xb8   | invokestatic    | 调用静态方法                                                 |
| 0xb9   | invokeinterface | 调用接口方法                                                 |
| 0xba   | --              |                                                              |
| 0xbb   | new             | 创建一个对象，并将其引用值压入栈顶                           |
| 0xbc   | newarray        | 创建一个指定原始类型（如int, float, char…）的数组，并将其引用值压入栈顶 |
| 0xbd   | anewarray       | 创建一个引用型（如类，接口，数组）的数组，并将其引用值压入栈顶 |
| 0xbe   | arraylength     | 获得数组的长度值并压入栈顶                                   |
| 0xbf   | athrow          | 将栈顶的异常抛出                                             |
| 0xc0   | checkcast       | 检验类型转换，检验未通过将抛出ClassCastException             |
| 0xc1   | instanceof      | 检验对象是否是指定的类的实例，如果是将1压入栈顶，否则将0压入栈顶 |
| 0xc2   | monitorenter    | 获得对象的锁，用于同步方法或同步块                           |
| 0xc3   | monitorexit     | 释放对象的锁，用于同步方法或同步块                           |
| 0xc4   | wide            | <待补充>                                                     |
| 0xc5   | multianewarray  | 创建指定类型和指定维度的多维数组（执行该指令时，操作栈中必须包含各维度的长度值），并将其引用值压入栈顶 |
| 0xc6   | ifnull          | 为null时跳转                                                 |
| 0xc7   | ifnonnull       | 不为null时跳转                                               |
| 0xc8   | goto_w          | 无条件跳转（宽索引）                                         |
| 0xc9   | jsr_w           | 跳转至指定32位offset位置，并将jsr_w下一条指令地址压入栈顶    |