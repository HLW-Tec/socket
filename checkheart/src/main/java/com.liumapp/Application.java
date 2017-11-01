package com.liumapp;

import com.liumapp.utils.TcpHeart;

public class Application {
    public static void main(String[] args) {
        TcpHeart t = new TcpHeart();
        t.start();
    }
}
