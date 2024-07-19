package com.kai.customize_annotation_practice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Target註解指定了RequireJwt註解可以應用的Java元素類型。
在這個例子中，它被設置為ElementType.METHOD，
這意味著RequireJwt註解只能被應用於方法上。
ElementType是一個枚舉，它還包括了其他值，
如TYPE（可以應用於類或接口）、FIELD（可以應用於字段）等。
 */
@Target(ElementType.METHOD)
/*
@Retention註解指定了RequireJwt註解的保留策略，
即這個註解在什麼時候有效。在這個例子中，
它被設置為RetentionPolicy.RUNTIME，
這意味著RequireJwt註解將在運行時保留，
因此可以通過反射在運行時被訪問。
其他的RetentionPolicy值包括
SOURCE（註解只在源碼中保留，並在編譯時被丟棄）和
CLASS（註解在編譯時被保留，但在運行時不可用）。
 */
@Retention(RetentionPolicy.RUNTIME)
/*
interface
是用來定義一個介面，裡面可以包含方法、屬性、類等等。可以實作，也可以不實作，
別的物件可以 implements 這個介面，然後實作裡面的方法。
@interface
是專門用來定義 Annotation 的，
 */
public @interface RequireJwt {
}

/*
當你在寫 Annotation 的時候，有些 Annotation 可以填入一些參數來讓使用者可以自定義某些行為。
不過在我們的範例裡面沒有用到，所以才是空白的。

我們可以定義以下這些東西來讓使用者可以自定義 Annotation 的行為：
基本類型：如int, float, boolean等。
String：字符串類型。
Class：類字面量，如MyClass.class。
枚舉：枚舉類型的值。
註解：其他註解。
以上類型的數組：例如int[], String[], MyEnum[]等。

以下是一個範例

// 定義一個枚舉類型作為註解的一部分
enum Status {
    ACTIVE, INACTIVE, PENDING
}

// 定義另一個註解類型作為註解的一部分
@interface AdditionalInfo {
    String description() default "No description";
}

// 定義ComplexAnnotation註解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 假設這個註解是用於類型上
public @interface ComplexAnnotation {
    // 基本類型
    int intValue() default 0;
    float floatValue() default 0.0f;
    boolean booleanValue() default true;

    // String
    String stringValue() default "Default";

    // Class
    Class<?> classValue() default Object.class;

    // 枚舉
    Status statusValue() default Status.ACTIVE;

    // 註解
    AdditionalInfo additionalInfo() default @AdditionalInfo;

    // 以上類型的數組
    int[] intArray() default {};
    String[] stringArray() default {};
    Status[] statusArray() default {};
    Class<?>[] classArray() default {};
}

而當別人要使用這個 Annotation 的時候，可以這樣使用：

// 假設有一個類，用於演示如何使用ComplexAnnotation註解
@ComplexAnnotation(
    intValue = 10,
    floatValue = 5.5f,
    booleanValue = true,
    stringValue = "Example",
    classValue = MyClass.class,
    statusValue = Status.ACTIVE,
    additionalInfo = @AdditionalInfo(description = "This is an example class"),
    intArray = {1, 2, 3},
    stringArray = {"one", "two", "three"},
    statusArray = {Status.ACTIVE, Status.INACTIVE},
    classArray = {MyClass.class, YourClass.class}
)
public class MyClass {
    // 類的實現細節
}
 */
