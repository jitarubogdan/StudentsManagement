package com.company;

public class ListOf<E> {
    private E[] elems = (E[]) new Object[10];

    public void add(E e,int i){
        elems[i] = e;
    }

    public E[] getAll(){
        return elems;
    }
}
