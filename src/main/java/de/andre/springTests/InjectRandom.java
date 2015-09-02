package de.andre.springTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by andreika on 9/1/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandom {
	int min();
	int max();
}
