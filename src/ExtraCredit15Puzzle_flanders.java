import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ExtraCredit15Puzzle_flanders extends Application implements EventHandler<ActionEvent> {

    GridPane p = new GridPane();
    Button[] pieces = new Button[16];

    //figure out how to activate this without lambda and handle()
    @Override
    public void handle (ActionEvent e) {
        Button clicked_btn;
        Alert message;
        int clicked_btn_index, blank_btn_index, prevInt,newInt,i;

        clicked_btn = (Button)e.getTarget();
        clicked_btn_index = findPiece(clicked_btn.getText());
        blank_btn_index = findPiece("");

        //I need to adjust this section
        p.getChildren().remove(0,16);
        if (clicked_btn_index==blank_btn_index+4
        ||clicked_btn_index==blank_btn_index+1 && blank_btn_index%4 != 3
        ||clicked_btn_index==blank_btn_index-4
        ||clicked_btn_index==blank_btn_index-1 && blank_btn_index%4 != 0){
            pieces[clicked_btn_index] = pieces[blank_btn_index];
            pieces[blank_btn_index] = clicked_btn;
        }
        for (i=0;i<16;i++){
            p.add(pieces[i],i%4,i/4);
        }
        if (pieces[15].getText().equals("")){
            prevInt = 1;
            for (i=1;i<15;i++){
                newInt = Integer.parseInt(pieces[i].getText());
                if (prevInt+1 != newInt){
                    break;
                }
                prevInt = newInt;
            }
            if (i==15){
                message = new Alert(AlertType.INFORMATION);
                message.setTitle("Complete!");
                message.setHeaderText("Congratulations!");
                message.setContentText("You won!");
                message.showAndWait();
                Platform.exit();

            }
        }
    }
    private int findPiece(String btn_text){
        int i;
        for (i=0;i<pieces.length;i++){
            if (pieces[i].getText().equals(btn_text)){
                return i;
            }
        }
        return -1;
    }

    private boolean solvable(Button[] p) {
        int n, blank, brn;
        int sum = 0, x, y;

        blank = findPiece("");
        brn = blank / 4 + 1;
        for (x = 0; x < 16; x++) {
            if (x == blank)
                continue;
            n = Integer.parseInt(p[x].getText());
            for (y = x+1; y < 16; y++) {
                if (y == blank)
                    continue;
                if (Integer.parseInt(p[y].getText()) < n)
                    sum ++;
            }
        }
        System.out.println("sum = " + sum + " brn = " + brn + " ans = " + (sum+brn));
        if ((sum+brn) % 2 == 0)
            return true;
        else
            return false;
    }

    private Button[] shuffle(Button[] ary){
        int i, random_index, length;
        Button i_value;
        length = ary.length;
        for (i=0;i<length;i++){
            i_value = ary[i];
            random_index = (int)(Math.random()*(length-i))+i;
            ary[i] = ary[random_index];
            ary[random_index] = i_value;
        }
        return ary;
    }


    @Override
    public void start(Stage primaryStage)
    {
        int i;
        Scene scene = new Scene(p,800,800);


        pieces[15] = new Button("");
        for (i=0; i<15; i++) {
            pieces[i] = new Button(Integer.toString(i+1));

        }
        for (Button btn : pieces){
            btn.setPrefSize(200,200);
            btn.setOnAction(click -> {
                handle(click);
            });
        }
        while (true) {
            shuffle(pieces);
            if (solvable(pieces)){
                break;
            }
        }

        for (i=0;i<16;i++){
            p.add(pieces[i],i%4,i/4);
        }

        // code goes here!
        primaryStage.setTitle("Fifteen Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
