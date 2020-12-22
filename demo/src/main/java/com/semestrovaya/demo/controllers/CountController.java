package com.semestrovaya.demo.controllers;

import com.semestrovaya.demo.models.Count;
import com.semestrovaya.demo.models.Role;
import com.semestrovaya.demo.models.Transaction;
import com.semestrovaya.demo.models.User;
import com.semestrovaya.demo.repo.CountRepository;
import com.semestrovaya.demo.repo.TransactionRepository;
import com.semestrovaya.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


@Controller
public class CountController {


    private static Logger logger = LoggerFactory.getLogger(CountController.class);


    @Autowired
    CountRepository countRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepo userRepo;

private void Logging(String comment) throws IOException {
    File file = new File("log.txt");
    FileWriter pw = new FileWriter(file,true);
    pw.write(comment);
    pw.write("\n");
    pw.close();

}

    @GetMapping("/")
    public String countMain(Model model) throws IOException {



        Iterable<Count> counts = countRepository.findAll();
        model.addAttribute("counts",counts);
        Logging("Visit a maim page");
        return "count";
    }


    @GetMapping("/count/add")
    public String countAdd() throws IOException {

        Logging("Starting add new count");
        return "count-add";
    }

    @PostMapping("/count/add")
    public String postCountAdd(@RequestParam String name, @RequestParam String comment, Model model) throws IOException {

        Count count = new Count(name,comment);
        countRepository.save(count);
        Logging("New count added");
        return "redirect:/";
    }

    @GetMapping("/count/{id}")
    public String countDetails(@PathVariable(value = "id") long id, Model model) throws IOException {
        if(!countRepository.existsById(id))
        {
            return "redirect:/";
        }
        Iterable<Transaction> transactions = transactionRepository.findAll();
        ArrayList<Transaction> res = new ArrayList<>();
        int sum = 0;
        for (Transaction tr: transactions) {
            if(tr.getCount_id() == id) {
                if(tr.getStatus() == 1) {
                    res.add(tr);
                    sum = sum + tr.getSum();
                }
            }
        }
        model.addAttribute("transactions",res);
        model.addAttribute("id",id);
        model.addAttribute("sum",sum);
        Logging("Watching transactions of count with id: "+ id);
        return "count-details";
    }

    @GetMapping("/count/{id}/addtrans")
    public String addTransToCount(@PathVariable(value = "id") long id, Model model) throws IOException {
        model.addAttribute("count-id",id);
        Logging("Starting add transaction to count with id: "+ id);
        return "count-addtrans";
    }

    @PostMapping("/count/{id}/addtrans")
    public String postAddTransToCount(@PathVariable(value = "id") long id, @RequestParam int sum, Model model) throws IOException {
        Transaction tr = new Transaction(sum,id);
        transactionRepository.save(tr);
        String result = "redirect:/count/"+id;
        Logging("Transaction to count with id: "+ id + " successfully added");
        return result;
    }

    @GetMapping("/count/{id}/deletetrans")
    public String deleteTrans(@PathVariable(value = "id") long id,Model model) throws IOException {
        Transaction tr = transactionRepository.findById(id).orElseThrow();
        tr.setStatus(0);
        transactionRepository.save(tr);
        Logging("Transaction with id: "+ id + " successfully deleted");
        return "redirect:/";
    }

    @PostMapping("/count/{id}/close")
    public String closeCount(@PathVariable(value = "id") long id,Model model) throws IOException {

        Count co = countRepository.findById(id).orElseThrow();
        Iterable<Transaction> transactions = transactionRepository.findAll();
        ArrayList<Transaction> res = new ArrayList<>();
        for (Transaction tr: transactions) {
            if(tr.getCount_id() == id) {

                    res.add(tr);
            }
        }
        for(Transaction tr : res) {
            transactionRepository.delete(tr);
        }

        countRepository.delete(co);
        Logging("Count with id: "+ id + "successfully closed");
        return "redirect:/";

    }

    @GetMapping("/count/{id}/editcomment")
    public String EditComment(@PathVariable(value = "id") long id,Model model) throws IOException {
        Count c = countRepository.findById(id).orElseThrow();
        model.addAttribute("id",id);
        model.addAttribute("comment",c.getComment());
        Logging("Start changing comment to count with id: "+ id);
        return "count-edit";
    }



    @PutMapping("/count/{id}/editcomment")
    public String EditComment(@PathVariable(value = "id") long id, @RequestParam String comment, Model model) throws IOException {
        Count c = countRepository.findById(id).orElseThrow();
        c.setComment(comment);
        countRepository.save(c);
        Logging("Change comment to count with id: "+ id);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }

}
