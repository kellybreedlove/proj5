package test;

import static org.junit.Assert.*;
import impl.ListSet;

import org.junit.Before;
import org.junit.Test;

public class LSTest extends SetTest {

    protected void reset() {
        testSet = new ListSet<String>();
    }

 
}
