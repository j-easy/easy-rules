/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.core;

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
            Class<?> expectedAnnotationType, Annotation actualAnnotation) {

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
