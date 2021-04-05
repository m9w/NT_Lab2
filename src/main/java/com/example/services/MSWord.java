package com.example.services;

import com.example.pojo.PojoOmdb;

import java.util.List;

public interface MSWord {
    byte[] generate(List<PojoOmdb> movieOMDBS);
}
