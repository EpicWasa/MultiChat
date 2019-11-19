package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ClientSide.ClientMainServer;

public class Controller implements Initializable {
    public TextArea TextAreaMain;
    public TextField TextFieldInput;
    public TextField EnterYourLogin;
    public Button ButtonSend;
    private BufferedWriter writer;

    public  void readMessage(String s){
        TextAreaMain.setText(TextAreaMain.getText()+s+"\n");
    }

    public void ButtonSendPressed(ActionEvent actionEvent) {
        if(!TextFieldInput.getText().isEmpty()) {

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            String Username = EnterYourLogin.getText().isEmpty() ? "Guest" : EnterYourLogin.getText();

            try {
            Main.getClient().writeMSG("["+formattedDate+"] "+ Username+": " + TextFieldInput.getText());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        TextFieldInput.clear();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(Main.getClient().getClient().getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
