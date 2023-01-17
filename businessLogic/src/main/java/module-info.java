module org.aapsasha.businessLogic {
    requires javaluator;
    requires alice;
    requires org.apache.commons.io;
    requires lombok;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.xml;
    exports org.aapsasha.businessLogic.InputOutput.InputConvert;
    exports org.aapsasha.businessLogic.InputOutput.OutputConvert;
    exports org.aapsasha.businessLogic.InputOutput;
    exports org.aapsasha.businessLogic.Calculation;
    exports org.aapsasha.businessLogic.Calculation.WithoutRegEx;
    exports org.aapsasha.businessLogic.Calculation.Library;
    exports org.aapsasha.businessLogic.InputOutput.OutputConvert.Encrypting;
    exports org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.ZIP;
    exports org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.Jar;
}