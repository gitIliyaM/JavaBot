import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    static String botToken = "6630556008:AAEPPxusitoRkWSstX_STdKDvbnWm4XyF1U";
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        BotSession botSession = api.registerBot(new Bot(botToken));
        /*
        Инструкция
        new TelegramBotsApi(DefaultBotSession.class); - рефлексия, получаем доступ к всем методам Класса DefaultBotSession
        registerBot(new Bot(botToken)); - создаем объект Бот передаем ему токен.

        onUpdateReceived(Update update) - в этом методе происходят все действия с Ботом
        update - принимает информацию
        update.getMessage(); - получить сообщение (текст или картинку или что еще возможно).

        runCommonCommand(message); - метод выводит три кнопки из Класса BotCommonCommands, в котором имеются аннотации с именами для кнопок,
        использует интерфейс AppBotCommand он служит родителем всех аннотаций.
        Сейчас метод не активен, так как используются другие аннотации для фильтров
        Как это работает - отправляется текст в метод. В методе создается объект класса с аннотациями, вызывает метод для получения
        всех методов класса, все методы помещаются в массив методов, далее цикл ФОР вытаскивает каждый метод, проверяет на аннотации
        наличие их вызывает метод получения аннотации, далее идет проверка переданного текста и имя из аннотации, совпадение запускает
        method.setAccessible(true); далее проверка на НУЛЛ, запуск отправки сообщения, получение чат ИД, отправка сообщение, возврат
        сендмессаж.

        runPhotoMessage(message); - метод выводит кнопки для выбора фильтра, расписывать не буду, много действий, но принцип получения кнопок
        аналогичен runCommonCommand(message);, только используется класса ФильтрОперация. В этом методе записывается выбор кнопки и чатИД в массив
        ХэшМап он является полем класса, поэтому используется в методе runPhotoFilter, где вытаскивается выбор пользователя - Кнопка, которая
        передается через лямбду в класс ИмаджОперацию и оттуда в класс ФильтрОперация.

        runPhotoFilter(message); - метод принимает фото, обрабатывает фото по требованию пользовталя - Кнопка

        Не стал расписывать все действия очень много, но все понятно
         */
    }
}
