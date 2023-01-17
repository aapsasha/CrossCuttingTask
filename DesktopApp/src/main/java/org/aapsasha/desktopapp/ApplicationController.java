package org.aapsasha.desktopapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.aapsasha.businessLogic.Calculation.CalculationContext;
import org.aapsasha.businessLogic.Calculation.Library.ExpressionParser;
import org.aapsasha.businessLogic.InputOutput.FileExtensions;
import org.aapsasha.businessLogic.InputOutput.InputConvert.ReadingManager;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Encrypting.AliceEncrypter;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.Jar.JarPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.ZIP.ZipPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.WritingManager;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class ApplicationController {

    public ListView<String> expressionsView;

    public ChoiceBox<String> outputFileType;

    public Button doneButton;

    public ListView<String> resultView;

    File mainFile;

    List<String> expressions;
    List<String> resultList;

    List<File> passwordlist;

    File resultFile;

    AliceEncrypter alice;

    public void FileButtonClicked(ActionEvent actionEvent){
        final FileChooser fileChooser = new FileChooser();
        mainFile = fileChooser.showOpenDialog(new Stage());

    }


    public void PasswordButtonClicked(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        passwordlist = fileChooser.showOpenMultipleDialog(new Stage());
    }

    public void ProcessButtonClicked(ActionEvent actionEvent) throws IOException, XMLStreamException {

        if(mainFile == null){
            return;
        }
        ReadingManager manager = new ReadingManager();
        expressions = manager.read(mainFile, passwordlist);

        ObservableList<String> tmpList = FXCollections.observableArrayList();
        tmpList.addAll(expressions);
        expressionsView.setItems(tmpList);

        doneButton.setVisible(true);

        CalculationContext context = new CalculationContext();
        context.setStrategy(new ExpressionParser());
        resultList =new ArrayList<>();
        for(String i : expressions){
            resultList.add(context.calculateExpression(i));
        }

        tmpList =FXCollections.observableArrayList();
        tmpList.addAll(resultList);
        resultView.setItems(tmpList);

        alice = new AliceEncrypter();
        WritingManager writingManager = new WritingManager();
        String type = outputFileType.getValue();
        resultFile = writingManager.write(resultList, switch (type){
            case "TXT" -> FileExtensions.txt;
            case "JSON" -> FileExtensions.json;
            case "XML" -> FileExtensions.xml;
            default -> throw new RuntimeException();
        });
    }



    public void outputTypeChanged(ActionEvent actionEvent) throws XMLStreamException, IOException {
        alice = new AliceEncrypter();
        WritingManager writingManager = new WritingManager();
        String type = outputFileType.getValue();
        resultFile.delete();
        resultFile = writingManager.write(resultList, switch (type){
            case "TXT" -> FileExtensions.txt;
            case "JSON" -> FileExtensions.json;
            case "XML" -> FileExtensions.xml;
            default -> throw new RuntimeException();
        });
    }

    public void AESButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        if(resultFile == null){
            return;
        }
        FileChooser fileChooser = new FileChooser();
        File passwordFile = fileChooser.showOpenDialog(new Stage());
        String password;
        try(BufferedReader reader = new BufferedReader(new FileReader(passwordFile))){
            password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resultFile = alice.encryptAES(resultFile,password);
    }

    public void DESButtonClicked(ActionEvent actionEvent) {
        if(resultFile == null){
            return;
        }
        FileChooser fileChooser = new FileChooser();
        File passwordFile = fileChooser.showOpenDialog(new Stage());
        String password;
        try(BufferedReader reader = new BufferedReader(new FileReader(passwordFile))){
            password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resultFile = alice.encryptDES(resultFile,password);
    }

    public void JarButtonClicked(ActionEvent actionEvent) {
        if(resultFile == null){
            return;
        }
        File tmp = resultFile;
        resultFile = JarPacker.pack(resultFile);
        tmp.delete();
    }

    public void ZipButtonClicked(ActionEvent actionEvent) {
        if(resultFile == null){
            return;
        }
        File tmp = resultFile;
        resultFile = ZipPacker.pack(resultFile);
        tmp.delete();
    }

    public void CloseButtonClicked(ActionEvent actionEvent) {
        mainFile = null;
        resultFile = null;
        Platform.exit();
    }
}