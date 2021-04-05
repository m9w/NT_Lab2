package com.example.tools;

import java.io.InputStream;

public interface HTTPLoader {
    HTTPLoader addParam(String name, String value);

    InputStream HTTPGet() throws HTTPLoaderImpl.HTTPLoaderException;
}
