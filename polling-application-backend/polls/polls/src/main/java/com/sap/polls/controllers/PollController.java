package com.sap.polls;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import com.sap.polls.SubmitPolls;
import com.sap.polls.PollListing;


@RestController
public class PollController {
    @PostMapping("/create")
    public String getfromurl(@RequestBody Map<String,Object> data){
        NewPolls newpoll = new NewPolls();
        return newpoll.createPoll(data);
    }

    @PostMapping("/draft")
    public String drafturl(@RequestBody Map<String,Object> data){
        Drafts drafts = new Drafts();
        return drafts.draftpolls(data);
    }

    @GetMapping("/{pollname}")
    public void submiturl(@PathVariable String pollname, @RequestParam String participant, @RequestParam int answer){
        SubmitPolls submitpolls = new SubmitPolls();
        submitpolls.submit(pollname,participant,answer);
    }

    @GetMapping("/draft/{pollname}")
    public void submitdrafturl(@PathVariable String pollname,@RequestParam String loggeduser){
        SubmitDraftPolls submitDraftpolls = new SubmitDraftPolls();
        submitDraftpolls.submitdrafts(pollname,"Polls","Drafts",loggeduser);
    }

    @GetMapping("/listPolls")
    public List<Map<String, Object>> listpollsurl(){
        PollListing polllisting = new PollListing();
        return polllisting.listPolls();
    }

    @GetMapping("/endpoll")
    public boolean endpollurl(@RequestParam String pollname){
        Endpolls endpolls = new Endpolls();
        endpolls.end_poll(pollname);
        return endpolls.returnEndVal(pollname);
    }

    @GetMapping("/endvals")
    public boolean endpollvalurl(@RequestParam String pollname){
        Endpolls endpolls = new Endpolls();
        return endpolls.returnEndVal(pollname);
    }


    @GetMapping("/listDrafts")
    public List<Map<String, Object>> listdraftsurl(@RequestParam String loggeduser){
        DraftListing draftlist = new DraftListing();
        return draftlist.listDrafts(loggeduser);
    }

    @GetMapping("/delete")
    public void deleteurl(@RequestParam String documentname, @RequestParam String currentuser){
        DeletePolls deletepolls = new DeletePolls();
        deletepolls.delete(documentname, currentuser);
    }

    @GetMapping("/deletedraft")
    public void deletedrafturl(@RequestParam String documentname, @RequestParam String currentuser){
        DeleteDrafts deletepolls = new DeleteDrafts();
        deletepolls.delete(documentname, currentuser);
    }

    @GetMapping("/answered")
    public void answeredurl(@RequestParam String uid){
        AnsweredPolls answeredPolls = new AnsweredPolls();
        answeredPolls.answered();
    }

    @GetMapping("/alreadyanswered/{pollname}")
    public boolean answeredalready(@PathVariable String pollname, @RequestParam String participant){
        AlreadyAnswered alreadyanswered = new AlreadyAnswered();
        return alreadyanswered.already(participant, pollname);
    }
}