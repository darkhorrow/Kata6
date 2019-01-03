package kata6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;

public class Kata6 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("/");
        iterate(file.listFiles());
    }
    
    private static Iterator<Integer> megabytes(Iterator<Long> iterator) {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Integer next() {
                return (int) (iterator.next() / (1024*1024));
            }
        };
    }

    private static Iterator<Long> lengthsOf(Iterator<File> iterator) {
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Long next() {
                return iterator.next().length();
            }
        };
    }

    private static <T> Iterator<T> iteratorOf(T[] items) {
        return new Iterator<T>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < items.length;
            }

            @Override
            public T next() {
                return items[index++];
            }
        };
    }
    
    public static void iterate(File[] items) throws SecurityException, IOException {
        Iterator<File> iter = iteratorOf(items);
        while (iter.hasNext()){
            File f = iter.next();
            if (f.isFile() && f.canRead() && !f.isHidden()){
                System.out.println("File: " + f.getAbsolutePath());
            }else if(f.isDirectory() && f.canRead() && !f.isHidden()){
                BasicFileAttributes attrs = Files.readAttributes(f.toPath(), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                if(!attrs.isOther() && attrs.isDirectory()){
                    System.out.println("Folder: " + f.getAbsolutePath());
                    File[] list = f.listFiles();
                    if(list != null) iterate(list);
                }
            }
            
        }
    }
    
}
