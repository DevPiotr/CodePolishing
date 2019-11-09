package com.codepolishing.engineer.model;

import java.io.*;
import java.util.List;

public class JavaFileCompiler {

    private File compileFile;
    private String sourceCode;
    private List<String> args;
    private String output = "";

    public JavaFileCompiler(String sourceCode, List<String> args) {
        this.sourceCode = sourceCode;
        this.args = args;
        compileFile = createFile();
    }

    private File createFile()
    {
        File file = new File("Solution.java");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null)
            return file;
        else
            throw new NullPointerException();
    }

    private void writeSourceCodeToFile() throws IOException {
        FileWriter fileWriter = new FileWriter(this.compileFile);
        fileWriter.write(sourceCode);
        fileWriter.close();
    }

    public void compileFile() throws IOException {
        writeSourceCodeToFile();

        //LINUX GNOME
        ProcessBuilder pbCreate = new ProcessBuilder("gnome-terminal","-e ","\"javac " + compileFile.getName()+"\"");
        pbCreate.start();
        ProcessBuilder pb = new ProcessBuilder("gnome-terminal","-e ","\"java Solution\"");
        //WINDOWS
        //ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","javac " + compileFile.getName() + " && java Solution " + getArgsToString());
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while(true)
        {
            line = br.readLine();
            if (line == null) {break;}
            output += line + '\n';
        }
        this.compileFile.delete();
        new File("Solution.class").delete();
    }

    private String getArgsToString()
    {
        if(args == null)
            return null;

        String correctForm = "";
        List<String> tmpList = args;
        int listLength = tmpList.toArray().length;
        for (int i = 0; i < listLength ; i++) {
            correctForm += tmpList.get(i) + " ";
        }
        return correctForm;
    }

    private boolean isFileJavaExtension()
    {
        return compileFile.getName().endsWith(".java") ? true : false;
    }
    public String getOutput()
    {
        return output;
    }
}

