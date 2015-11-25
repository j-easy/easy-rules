package org.easyrules.util;

import org.junit.Test;

import java.lang.annotation.*;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void findAnnotationWithClassWhereAnnotationIsPresent() {
        Annotation foo = Utils.findAnnotation(Foo.class, AnnotationIsPresent.class);

        assertCorrectAnnotationIsFound(Foo.class, foo);
    }

    @Test
    public void findAnnotationWithClassWhereAnnotationIsPresentViaMetaAnnotation() {
        Annotation foo = Utils.findAnnotation(Foo.class, AnnotationIsPresentViaMetaAnnotation.class);

        assertCorrectAnnotationIsFound(Foo.class, foo);
    }

    @Test
    public void findAnnotationWithClassWhereAnnotationIsNotPresent() {
        Annotation foo = Utils.findAnnotation(Foo.class, Object.class);

        assertNull(foo);
    }

    @Test
    public void isAnnotationPresentWithClassWhereAnnotationIsPresent() {
        assertTrue(Utils.isAnnotationPresent(Foo.class, AnnotationIsPresent.class));
    }

    @Test
    public void isAnnotationPresentWithClassWhereAnnotationIsPresentViaMetaAnnotation() {
        assertTrue(Utils.isAnnotationPresent(Foo.class, AnnotationIsPresentViaMetaAnnotation.class));
    }

    @Test
    public void isAnnotationPresentWithClassWhereAnnotationIsNotPresent() {
        assertFalse(Utils.isAnnotationPresent(Foo.class, Object.class));
    }

    private static void assertCorrectAnnotationIsFound(
            Class expectedAnnotationType, Annotation actualAnnotation) {

        assertNotNull(actualAnnotation);
        assertEquals(expectedAnnotationType, actualAnnotation.annotationType());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface Foo {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Foo
    private @interface MetaFoo {
    }

    @Foo
    private static final class AnnotationIsPresent {
    }

    @MetaFoo
    private static final class AnnotationIsPresentViaMetaAnnotation {
    }
}
