package ir.darkdeveloper.sec01.Assignment;

import java.io.IOException;

import ir.darkdeveloper.utils.Util;

public class Assignment {

    public static void main(String[] args) throws IOException {

        FilesService.write("file.txt", "wrote data ", true)
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete("write"));
        FilesService.read("file.txt")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete("read"));
        // FilesService.delete("file.txt")
        //         .subscribe(Util.onNext(), Util.onError(), Util.onComplete("delete"));
    }

}
