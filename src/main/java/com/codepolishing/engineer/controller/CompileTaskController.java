package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.CompileTask;
import com.codepolishing.engineer.model.JavaFileCompiler;
import com.codepolishing.engineer.repository.CompileTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

@Controller
@RequestMapping("/sections")
public class CompileTaskController {

    @Autowired
    private CompileTaskRepository compileTaskRepository;

    @GetMapping("/task")
    public String showCompileTask(Model model)
    {
        CompileTask compileTask = compileTaskRepository.findById(1);

        String taskText = compileTask.getTaskContent();
        model.addAttribute("taskText", taskText);

        String preSourceCode = compileTask.getInitCode();
 
        model.addAttribute("preSourceCode", preSourceCode);

        return "compile_task";
    }
    @PostMapping("/task")
    public String compileTextAndShowResult(Model model, @RequestParam("sourceCode") String sourceCode) throws IOException {

        /*String answer = "";
        CompileTask compileTask = compileTaskRepository.findById(1);

        String currentSourceCode = sourceCode;

        String dane = compileTask.getInputs();
        //Sprawdzam czy dane są w bazie. Jesli tak, to podmieniam System.in na Input z danych wejściowych
        if(dane != null && dane != "")
        sourceCode = sourceCode.replace("public static void main(String[] args){","public static void main(String[] args){ String dane = \"" + dane + "\"; InputStream daneStream = new ByteArrayInputStream(dane.getBytes());\n" +
                "System.setIn(daneStream);");

        //Kompiluje rozwiazanie użytkownika
        JavaFileCompiler javaFileCompiler = new JavaFileCompiler(sourceCode,null);
        javaFileCompiler.compileFile();
        String output = javaFileCompiler.getOutput();

        //Zmieniam System.in na odczytywanie z danych
        String correctCode = compileTask.getProperCode();
        correctCode = correctCode.replace("public static void main(String[] args){","public static void main(String[] args){ String dane = \"" + dane + "\"; InputStream daneStream = new ByteArrayInputStream(dane.getBytes());\n" +
                "System.setIn(daneStream);");
        //Kompiluję rozwiazanie z bazy danych
        JavaFileCompiler java2FileCompiler = new JavaFileCompiler(correctCode,null);
        java2FileCompiler.compileFile();
        String output2 = java2FileCompiler.getOutput();

        //Porównuje wyniki
        if(output2.equals(output))
        {
            answer = "OK!";
        }else
        {
            answer = "ZLE!";
        }
        System.out.println("output = " + output +
                "\noutput2 = " +
                output2);


        String taskText = "Napisz program, który dodaje dwie liczby";
        model.addAttribute("taskText", taskText);
        model.addAttribute("preSourceCode",currentSourceCode);
        model.addAttribute("console",answer);

        return "compile_task";*/

        CompileTask compileTask = compileTaskRepository.findById(1);
        // Wyciągniecie treści zadania z bazy
        String taskText = compileTask.getTaskContent();

        // Wyciagniecie kodu roboczego z bazy
        String codeToCompile = compileTask.getCompileCode();

        // Dodanie danych testowych
        codeToCompile = codeToCompile.replace("String input = \"\";",
                "String input = \"" + compileTask.getInputs() + "\";");

        // Podmiana kodu domyślnego na kod poprawny
        codeToCompile = codeToCompile.replace(compileTask.getInitCode(),compileTask.getProperCode());

        // Kompilacja kodu źródłowego poprawnego (wzoru)
        JavaFileCompiler jfcc = new JavaFileCompiler(codeToCompile,null);
        jfcc.compileFile();
        String correctOutput = jfcc.getOutput();

        // Podmiana kodu poprawnego na kod użytkownika
        codeToCompile = codeToCompile.replace(compileTask.getProperCode(),sourceCode);

        //Kompilacja kodu źródłowego użytkownika
        JavaFileCompiler jfc = new JavaFileCompiler(codeToCompile,null);
        jfc.compileFile();
        String userOutput = jfc.getOutput();

        // Sprawdzenie poprawności rozwiazania
        String answer;
        if(correctOutput.equals(userOutput))
            answer = "OK!";
        else
            answer = "ZLE!";

        System.out.println("userOutput: "+userOutput);
        System.out.println("correctOutput: "+correctOutput);

        model.addAttribute("taskText", taskText);
        model.addAttribute("preSourceCode",sourceCode);
        model.addAttribute("console",answer);

        return "compile_task";
    }


}
