package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.model.JavaFileCompiler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.applet.AppletResourceLoader;

import java.io.IOException;

@Controller()
public class CompileTaskController {


    @GetMapping("/task")
    public String showCompileTask(Model model)
    {
        String taskText = "Napisz program, który dodaje dwie liczby";
        model.addAttribute("taskText", taskText);
        String preSourceCode = "public class Solution {\n" +
                "  public static void main(String[] args){\n" +
                "  }\n" +
                "}";
        model.addAttribute("preSourceCode", preSourceCode);

        return "compile_task";
    }
    @PostMapping("/task")
    public String compileTextAndShowResult(Model model, @RequestParam("sourceCode") String sourceCode) throws IOException {
        String currentSourceCode = sourceCode;

        JavaFileCompiler javaFileCompiler = new JavaFileCompiler(sourceCode,null);
        javaFileCompiler.compileFile();
        String output = javaFileCompiler.getOutput();

        String taskText = "Napisz program, który dodaje dwie liczby";
        model.addAttribute("taskText", taskText);
        model.addAttribute("preSourceCode",currentSourceCode);
        model.addAttribute("console",output);

        return "compile_task";
    }


}
