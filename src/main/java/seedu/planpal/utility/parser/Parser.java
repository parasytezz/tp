package seedu.planpal.utility.parser;

import seedu.planpal.exceptions.InvalidModeException;
import seedu.planpal.modes.activities.ActivityManager;
import seedu.planpal.modes.contacts.ContactManager;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.modes.expenses.managers.ExpenseManager;
import seedu.planpal.utility.Ui;
import seedu.planpal.utility.filemanager.BackUpManager;
import seedu.planpal.utility.filemanager.FileManager;
import seedu.planpal.utility.parser.modeparsers.ActivityParser;
import seedu.planpal.utility.parser.modeparsers.ContactParser;
import seedu.planpal.utility.parser.modeparsers.ExpenseParser;
import java.util.Scanner;

public class Parser {
    protected static final String BACK_UP_COMMAND = "/b/";
    protected static final String RESTORE_COMMAND = "/r/";
    protected static final String CLEAR_COMMAND = "clear";
    protected static final String BYE_COMMAND = "bye";
    protected static final String ADD_COMMAND = "add";
    protected static final String DELETE_COMMAND = "delete";
    protected static final String SET_CATEGORY_COMMAND = "category";
    protected static final String SEARCH_CATEGORY_COMMAND = "search";
    protected static final String EDIT_COMMAND = "edit";
    protected static final String FIND_COMMAND = "find";
    protected static final String LIST_COMMAND = "list";
    protected static final String EXIT_MODE_COMMAND = "exit";
    private static final String CONTACT_MANAGER = "1";
    private static final String EXPENSE_MANAGER = "2";
    private static final String ACTIVITY_MANAGER = "3";
    protected FileManager fileManager;
    protected ContactManager contactManager;
    protected ExpenseManager expenseManager;
    protected ActivityManager activityManager;

    public Parser(){
        this.fileManager = new FileManager();
        this.contactManager = new ContactManager();
        this.expenseManager = new ExpenseManager();
        this.activityManager = new ActivityManager();
    }

    protected void loadFiles() throws PlanPalExceptions {
        expenseManager.getBudgetManager().setAllBudget(fileManager.loadAllValues("budgets"));
        fileManager.loadAllLists(expenseManager, "expenses");
        fileManager.loadAllLists(contactManager, "contacts");
        fileManager.loadAllLists(activityManager, "activities");
    }

    private String getCommand(String currentMode){
        Scanner in = new Scanner(System.in);
        System.out.println("Current Mode: " + currentMode);
        System.out.print("Enter command: ");
        return in.nextLine();
    }

    public boolean processCommand(String modeInput) throws PlanPalExceptions {
        boolean isProcessing = true;
        loadFiles();
        while (isProcessing) {
            switch (modeInput){
            case CONTACT_MANAGER:
                try {
                    String commandForContact = getCommand("CONTACT_MANAGER");
                    ContactParser contactParser = new ContactParser(contactManager);
                    isProcessing = contactParser.processCommand(commandForContact);
                } catch (PlanPalExceptions e) {
                    Ui.print(e.getMessage());
                }
                break;

            case EXPENSE_MANAGER:
                try {
                    String commandForExpense = getCommand("EXPENSE_MANAGER");
                    ExpenseParser expenseParser = new ExpenseParser(expenseManager);
                    isProcessing = expenseParser.processCommand(commandForExpense);
                } catch (PlanPalExceptions e) {
                    Ui.print(e.getMessage());
                }
                break;

            case ACTIVITY_MANAGER:
                try {
                    String commandForActivity = getCommand("ACTIVITY_MANAGER");
                    ActivityParser activityParser = new ActivityParser(activityManager);
                    isProcessing = activityParser.processCommand(commandForActivity);
                } catch (PlanPalExceptions e) {
                    Ui.print(e.getMessage());
                }
                break;

            case CLEAR_COMMAND:
                Ui.clearScreen();
                isProcessing = false;
                break;

            case BYE_COMMAND:
                Ui.printByeMessage();
                System.exit(0);
                break;

            case BACK_UP_COMMAND:
                BackUpManager.backupData();
                Ui.print("Backup Complete!");
                isProcessing = false;
                break;

            case RESTORE_COMMAND:
                BackUpManager.restoreData();
                Ui.print("Restore Complete!");
                isProcessing = false;
                break;

            default:
                throw new InvalidModeException();
            }
        }
        return false;
    }
}
