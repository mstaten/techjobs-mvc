package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        String checkedColumn = "all";
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("checkedColumn", checkedColumn);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

    @RequestMapping(value = "results")
    public String results(@RequestParam String searchType, @RequestParam String searchTerm, Model model) {

        // look up results via JobData class
        ArrayList<HashMap<String, String>> jobs;

        if (searchType.equals("all")) {
            jobs = JobData.findByValue(searchTerm);
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        // pass title
        model.addAttribute("title", jobs.size() + " Result(s)");

        // pass results into search.html view via the model
        model.addAttribute("jobs", jobs);

        // pass ListController.columnChoices to the view
        model.addAttribute("columns", ListController.columnChoices);

        // right before returning, somehow make view remember to check searchTerm next time it displays
        String checkedColumn = searchType;
        model.addAttribute("checkedColumn", checkedColumn);
        return "search";
    }

}
