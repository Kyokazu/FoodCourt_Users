package com.foodcourt.proyect.infrastructure.common;

import java.util.stream.Stream;

public interface MapperBase<A, B> {
    B AToB(A a);

    A BToA(B b);

    Stream<B> AToB(Stream<A> a);

    Stream<A> BToA(Stream<B> b);
}
