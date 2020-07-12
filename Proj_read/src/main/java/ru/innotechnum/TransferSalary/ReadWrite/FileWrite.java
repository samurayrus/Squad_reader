package ru.innotechnum.TransferSalary.ReadWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class FileWrite {
    private String path;
    private FileWriter fw;
    private BufferedWriter bw;
    private File file;

    public FileWrite(String path)
    {
            this.path=path;
            filecreator();
    }

    private void filecreator(){ //Создание файла для записи результатов или же поиск его и добавление первой строки с датой
        try {
            if(path==null)
            {file = new File("SquadTransfer.txt"); file.createNewFile(); System.out.println("File [SquadTransfer.txt] has been created on default path " + file.getAbsolutePath() );} //Создает новый файл, если не был указан в аргументах (пересоздает уже созданный там)
            else
            {file = new File(path);System.out.println("File Path "+file.getAbsolutePath());}

        fw = new FileWriter(file,false); //true чтобы не перезаписывать файл, а добавлять в конец.
        bw = new BufferedWriter(fw);
        bw.write("_______________________"+new Date().toString()+"_________________________");
        bw.newLine();
        bw.flush();

        } catch (IOException e) {
            System.out.println("File for write not found");
        }
    }


    public void WriteAnswer(String answer) {
        try {
            String ans[] = answer.split("\n");  //Чтобы ответ был не в одну строку, а блоком. Так читабельней
            for(int h=1;h<ans.length;h++)
            {
                bw.write(ans[h]);
                bw.newLine();
                bw.flush();
            }
            bw.newLine();
        } catch (IOException e) {
           System.out.println("IOException FileWrite");
        }

    }
}