
import commands.AppBotCommand;
import commands.BotCommonCommands;
import functions.FilterOperation;
import functions.ImageOperation;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.ImageUtils;
import utils.PhotoMessageUtils;

//import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    String botToken;
    @Override
    public String getBotUsername() {
        return "JavaBot";
    }
    Class[] commandClasses = new Class[]{BotCommonCommands.class};
    HashMap<String, Message> messages = new HashMap<>();

    public Bot(String botToken) {
        super(botToken);
        this.botToken = botToken;
    }
    // устаревший метод !
    /*@Override
    public String getBotToken(){
        return "6630556008:AAEPPxusitoRkWSstX_STdKDvbnWm4XyF1U";
    }*/
    @Override
    public void onUpdateReceived(Update update) {
        // Блок получения из чата
        //String fileName = "C:\\Users\\30.07.2018\\Desktop\\www\\2.jpg";
        Message message = update.getMessage();
        try {
            SendMessage responseTxtMessage = runCommonCommand(message);
            if(responseTxtMessage != null){
                execute(responseTxtMessage);
            }
            responseTxtMessage = runPhotoMessage(message);
            if(responseTxtMessage != null){
                execute(responseTxtMessage);
            }
            Object responseMediaMessage = runPhotoFilter(message);
            if(responseMediaMessage != null){
                if(responseMediaMessage instanceof SendMediaGroup){
                    execute((SendMediaGroup) responseMediaMessage);
                } else if(responseMediaMessage instanceof SendMessage){
                    execute((SendMessage) responseMediaMessage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        /*try {
            SendMediaGroup responseMediaMessage = runPhotoFilter(message);
            if(responseMediaMessage != null){
                execute(responseMediaMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        //String chatId = message.getChatId().toString();
        /*try {
            SendMessage sendMessage = new SendMessage();
            SendMessage response = runCommonCommand(message);
            sendMessage.setChatId(chatId);
            sendMessage.setText(response);
            execute(sendMessage);
        } catch (InvocationTargetException | IllegalAccessException | TelegramApiException | NoSuchMethodException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }*/
        /*SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Это твое сообщение " + message.getText());*/

        /*try {
            //execute(sendMessage);
            ArrayList<String> photoPaths = new ArrayList<>(PhotoMessageUtils.savePhotos(getFilesByMessage(message), botToken));
            for (String path: photoPaths) {
                PhotoMessageUtils.processingImage(path);
                execute(preparePhotoMessage(path, chatId));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        //System.out.println(message.getText());
        //String response = message.getFrom().getId().toString();

        /*PhotoSize photoSize = message.getPhoto().get(0);
        final String fieldId = photoSize.getFileId();
        try {
            final org.telegram.telegrambots.meta.api.objects.File file = sendApiMethod(new GetFile(fieldId));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }*/
        /*PhotoSize photoSize = message.getPhoto().get(0);
        final String fieldId = photoSize.getFileId();
        try {
            final org.telegram.telegrambots.meta.api.objects.File file = sendApiMethod(new GetFile(fieldId));
            final String imageUrl = "https://api.telegram.org/file/bot"+botToken+"/"+file.getFilePath();
            saveImage(imageUrl, fileName);
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            processingImage(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        // Блок отправки в чат


        //String txtSend = "C:\\Users\\30.07.2018\\Desktop\\www\\1.jpg";
        //File file = new File(txtSend);
    }
    private SendMediaGroup preparePhotoMessage(List<String> localPaths, ImageOperation operation, String chatId) throws Exception { //
        SendMediaGroup mediaGroup = new SendMediaGroup();
        ArrayList<InputMedia> medias = new ArrayList<>();
        for(String path: localPaths){
            InputMedia inputMedia = new InputMediaPhoto();
            PhotoMessageUtils.processingImage(path, operation);
            inputMedia.setMedia(new java.io.File(path), path);
            //inputMedia.setNewMediaFile(new java.io.File(path));
            medias.add(inputMedia);
        }
        mediaGroup.setMedias(medias);
        mediaGroup.setChatId(chatId);

        return mediaGroup;
    }
    /*private SendPhoto preparePhotoMessage(String localPath, String chatId){
        SendPhoto sendPhoto = new SendPhoto();

        sendPhoto.setReplyMarkup(getKeyboard());
        sendPhoto.setChatId(chatId);

        InputFile newFile = new InputFile(); // InputFile newFile = new InputFile(localPath);
        newFile.setMedia(new java.io.File(localPath));

        sendPhoto.setPhoto(newFile);
        sendPhoto.setCaption("Это картинка");
        return sendPhoto;
    }*/
    private ReplyKeyboardMarkup getKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        ArrayList<KeyboardRow> allKeyboardRows = new ArrayList<>(getKeyboardRows(BotCommonCommands.class));
        //allKeyboardRows.addAll(getKeyboardRow(FilterOperations.class));

        replyKeyboardMarkup.setKeyboard(allKeyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
        //ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
       /*Method[] methods = someClass.getMethods();
        int columnCounter = 3;
        int rowsCount = methods.length / columnCounter + ((methods.length % columnCounter == 0) ? 0 : 1);
        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
            KeyboardRow row = new KeyboardRow();
            for (int colomnIndex = 0; colomnIndex < columnCounter; colomnIndex++) {
                int index = rowIndex * columnCounter + colomnIndex;
                if(index >= methods.length){
                    continue;
                }
                Method method = methods[rowIndex * columnCounter + colomnIndex];
                KeyboardButton keyboardButton = new KeyboardButton(method.getName());
                row.add(keyboardButton);
            }
            keyboardRows.add(row);
        }*/
        /*for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            method.getName();
        }*/

        /*for (int i = 0; i < 3; i++) {
            KeyboardRow row = new KeyboardRow();
            for (int j = 0; j < 3; j++) {
                KeyboardButton keyboardButton = new KeyboardButton("button" + (i*3+j+1));
                row.add(keyboardButton);
            }

        }*/
        /*replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;*/
    }
    private ArrayList<KeyboardRow> getKeyboardRows(Class someClass){
        Method[] classMethods = someClass.getDeclaredMethods();
        ArrayList<AppBotCommand> commands = new ArrayList<>();
        for (Method method: classMethods) {
            if(method.isAnnotationPresent(AppBotCommand.class)){
                commands.add(method.getAnnotation(AppBotCommand.class));
                //AppBotCommand command = method.getAnnotation(AppBotCommand.class);

            }
        }
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        int columnCounter = 3;
        int rowsCount = commands.size() / columnCounter + ((commands.size() % columnCounter == 0) ? 0 : 1);
        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
            KeyboardRow row = new KeyboardRow();
            for (int colomnIndex = 0; colomnIndex < columnCounter; colomnIndex++) {
                int index = rowIndex * columnCounter + colomnIndex;
                if(index >= commands.size()){
                    continue;
                }

                AppBotCommand command = commands.get(rowIndex * columnCounter + colomnIndex);
                KeyboardButton keyboardButton = new KeyboardButton(command.name());
                row.add(keyboardButton);
            }
            keyboardRows.add(row);
        }
        return keyboardRows;
    }
    private SendMessage runCommonCommand(Message message) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String txt = message.getText();
        BotCommonCommands commands = new BotCommonCommands();
        Method[] classMethods = commands.getClass().getDeclaredMethods();
        for (Method method: classMethods) {
            if(method.isAnnotationPresent(AppBotCommand.class)) {
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                if(command.name().equals(txt)){
                    method.setAccessible(true);
                    String responseTxt = (String) method.invoke(commands);
                    /*
                    Method method: classMethods - цикл, в котором достаются по одному объекту методу.
                    BotCommonCommands commands = new BotCommonCommands(); - объект (копия) класса, в котором имеются данные.
                    method.invoke(commands) - вызывает метод из класса BotCommonCommands этот метод (из BotCommonCommands) должен быть
                    аналогичный method.
                     */
                    if(responseTxt != null){
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId().toString());
                        sendMessage.setText(responseTxt);
                        return sendMessage;
                    }
                    //return (String) method.invoke(commands); // (String) method.invoke(txt);
                }
            }
        }
        return null;
    }
    private SendMessage runPhotoMessage(Message message){
        List<File> files = getFilesByMessage(message);
        if(files.isEmpty()){
            return null;
        }
        String chatId = message.getChatId().toString();
        messages.put(chatId, message);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> allKeyboardRows = new ArrayList<>(getKeyboardRows(FilterOperation.class));

        replyKeyboardMarkup.setKeyboard(allKeyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите фильтр");
        return sendMessage;
    }
    private Object runPhotoFilter(Message newMessage) throws Exception {
        /*
        для получения и отправления текста использовать String txt = message.getText()
        аналогично для заголовка caption использовать   String txt = message.getCaption()
         */
        String txt = newMessage.getText();
        ImageOperation operation = ImageUtils.getOperation(txt);
        if(operation == null) return null;

        String chatId = newMessage.getChatId().toString();
        Message photoMessage = messages.get(chatId);
        if(photoMessage != null){
            List<File> files = getFilesByMessage(photoMessage);
            List<String> paths = PhotoMessageUtils.savePhotos(files, botToken);
            return preparePhotoMessage(paths, operation, chatId); // возможно сделать условие возвращение null
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Отправьте фото, чтобы воспользоваться фильтром");
            return sendMessage;
        }
    }
    private String runCommand(String txt) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        /*for (int i = 0; i < commandClasses.length; i++) {
            Constructor<Class> constructor = Class.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Class<?> ourInstance = constructor.newInstance();
            Method[] classMethods = ourInstance.getClass().getDeclaredMethods(); // ourInstance.getClass().getDeclaredMethods();
            for (Method method: classMethods) {
                if(method.isAnnotationPresent(AppBotCommand.class)) {
                    AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                    if(command.name().equals(txt)){
                        method.setAccessible(true);
                        return (String) method.invoke(ourInstance); // (String) method.invoke(txt);
                    }
                }
            }
        }*/
        BotCommonCommands commands = new BotCommonCommands();
        Method[] classMethods = commands.getClass().getDeclaredMethods();
        for (Method method: classMethods) {
            if(method.isAnnotationPresent(AppBotCommand.class)) {
                AppBotCommand command = method.getAnnotation(AppBotCommand.class);
                if(command.name().equals(txt)){
                    method.setAccessible(true);
                    return (String) method.invoke(commands); // (String) method.invoke(txt);
                }
            }
        }
        return "";
    }
    private List<File> getFilesByMessage(Message message) {
        List<PhotoSize> photoSizes = message.getPhoto();
        if(photoSizes == null){
            return new ArrayList<>();
        }
        ArrayList<File> files = new ArrayList<>();
        for (PhotoSize photoSize: photoSizes) {
            final String fieldId = photoSize.getFileId();
            try {
                files.add(sendApiMethod(new GetFile(fieldId)));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        return files;
    }
    /*public void processingImage(String fileName) throws Exception {
        final BufferedImage image = getImage(fileName);
        final RgbMaster rgbMaster = new RgbMaster(image);
        rgbMaster.changeImage(FilterOperations::greyscale);
        ImageUtils.saveImage(image,"C:\\Users\\30.07.2018\\Desktop\\www\\2.png");
    }*/
    /*public void saveImage (String url, String fileName) throws IOException {
        URL urlModel = new URL(url);
        InputStream inputStream = urlModel.openStream();
        //Path path = Paths.get(fileName);
        //Files.copy(inputStream,path);
        //inputStream.close();
        OutputStream outputStream = new FileOutputStream(fileName);
        byte[] b = new byte[2048];
        int length;
        while ((length = inputStream.read()) != -1){
            outputStream.write(b,0,length);
        }
        inputStream.close();
        outputStream.close();
    }*/
}
