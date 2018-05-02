package org.vrhel.graphics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * Indicates that something has no function, and
 * it is usually there to serve as a future
 * feature.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
@Target({ TYPE, FIELD, METHOD })
public @interface Useless {

}
