package com.vempower.eezyclinic.interfaces;

import java.io.File;

public interface ImageProcessListener {
    void callShowImageSourceDialog(int id);
    void setImage(File file, int responseId);
}
