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
            file.setExecutable(true);
            file.setWritable(true);
            file.setReadable(true);
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

        String osName = System.getProperty("os.name");
        ProcessBuilder pb = null;

        if(osName.equals("Linux")) {
            //LINUX
            pb = new ProcessBuilder("bash", "-c", "javac Solution.java && java Solution");
        }else {
            //WINDOWS
            pb = new ProcessBuilder("cmd.exe", "/c", "javac " + compileFile.getName() + " && java Solution " + getArgsToString());
        }
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

