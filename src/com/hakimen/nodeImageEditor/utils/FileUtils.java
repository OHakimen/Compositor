package com.hakimen.nodeImageEditor.utils;

import java.io.File;
import java.util.ArrayList;

public class FileUtils {
    public static File[] getBatch(File folder, String... terminators) {
        ArrayList<File> files = new ArrayList<>();
        if (folder.isDirectory()) {
            for (String s : folder.list()) {
                for (String term : terminators){
                    if (s.endsWith(term)) {
                        files.add(new File(folder.getPath()+"/"+s));
                    }
                }
            }
        }
        File[] toReturn = new File[files.size()];
        for (int i = 0; i < files.size(); i++) {
            toReturn[i] = files.get(i);
        }
        return toReturn;
    }

    public static File createFilesFor(File f){
        f.mkdirs();
        return f;
    }
}
