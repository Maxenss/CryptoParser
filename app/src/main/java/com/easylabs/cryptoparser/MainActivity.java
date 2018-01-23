package com.easylabs.cryptoparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Что-то связанное с Web
    // CryptoParser
    // Темы:
    // 1. Жизненный цикл приложения.
    // 2. Базовые VIew-компоненты.
    // 3. Работа с сетью на  уровне пвттернка Request/Response
    // 4. Работа с сетью на Андроиде.
    // 5. Аснхронные потоки в Android.
    // 6. Парсинг данных, таких как: HTML, JSON
    // 7. Android Manifest

    TextView tv;

    // Вызывается тогда, когла основные компоненты Activity прогрузились, в этом методе прописываем логику инициализации
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        // Создадим асинхр. поток исполнения класса WebTask
        WebTask webTask = new WebTask();
        // Запускаем поток
        webTask.execute();

    }

    private void lecture(){

        // Жизненный цикл приложения:
        //      1. Запуск приложения.
        //      2. Рабочий процесс приложения
        //          2.1 Основной поток исполнения, занимается отрисовкой GUI, и обработкой пользовтельских действий.
        //          2,2 Могут генерировать дополнительные потоки данных, или исполнения.
        //          2.3 Потоки исполнения могут быть синхронными, могут быть асинхронными (AsyncTask).
        //          2.4 Помимо основного потока выполнеия GUI, программа может также создавать сервисы.
        //      3. Временная остановка выполнения приложения.
        //      3.1. Пользователь свернул приложение
        //      4. Завершение работы приложения.

        // Activity (окно, открытое приложение), Service(асинхронный поток, выполняющийся в заднем фоне)

        // Работа с сетью, на уровне паттерна Requset/Response
        // клиент ---> сервер
        // клиент <--- сервер

        // Получаем html-разметку страницы
        // По адресу https://www.google.com.ua/ передаем стандартный GET-запрос.
        // клиент ---> сервер
        // Сервер обрабатывает этот запрос, XXXXXXXXXXXXXX, и возвращает нам ответ в виде HTML-разметки (строку)
        // клиент <--- сервер

        // Получить данные для Android приложения
        // По адресу https://api.vk.com/?id=1
        // клиент(Android) ---> сервер(php)
        // Сервер обрабатывает этот запрос, делает выборку необхожимых данных об учетке пользователя с id = 1, такие
        //          данные, как имя, фамилия, аватар и т.д., и формирует ответ в виде JSON-строки, и возвращает строку клиенту
        // клиент <--- сервер, клиент, обрабатывает эту строку(парсит её), и отображает полезные данные пользователю
        // {"firstName": "Maximka", "isProgrammer": true}
        // GSOM, JSON

        // Какие бывают http(s)-запрос:
        // 1. GET-запрос, запрос на получения данных. Как правило, используется для  получения html разметки, и получения полезных данных.
        //      В теле get-запроса, не могут передаваться данные. Но в адресе запроса (url) данные могут передаваться.
        // 2. POST-запрос, запрос на получения данных, с отправкой данных. Как правило используется при добавлениях данных в
        // бд на сервере, при поиске, авторизации на сервер и т.д. В теле запроса, могут передаваться данные.
        // 3. PUT
        // 4. DELETE
        // 5. UPDATE

        // Работа с сетью на Андроиде
        // 1. Указать в манифисте приложения, что приложени. необходим доступ к сети
        // 2. Создать отдельный асинхронный поток, для работы с сетью


        // CallBack - обратный вызов, как правило применяется в терминологии связанной с потоками

        // Есть приложение. В нем два Activity 1 и 2.
        // Вы запустили приложение, перешли в Activity 2, и свернули приложение.
        // Какое-то время используете телефон, и решаете вернуть в приложение
        // Что произойдет:
        // 1. Вы вернетесь к тому моменту, где остановились.
        // 2. Приложение запустить сначала.
        // Зависит от ОЗУ. Приложение помещается в стек приложений.

    }

    // Вызывается тогда, когда Activity получает возможность воспринимать пользовтельский ввод
    // После того как отработал onCreate
    // После того, как возобниволось Activity
    @Override
    protected void onResume() {
        super.onResume();
    }

    // Вызывается тогда, когда пользователь свернул приложение
    @Override
    protected void onPause() {
        super.onPause();
    }

    // Вызывается тогда, когда Android определяет, что нужно удалить Activity из RAM
    @Override
    protected void onStop() {
        super.onStop();
    }

    // Вызывается тогда, когда Activity начало запускаться
    @Override
    protected void onStart() {
        super.onStart();
    }

    String responseData = "";
    public boolean googleRequset(){
        try {
            // Указываем адрес для отправки запроса
            URL obj = new URL("https://www.google.com.ua/");
            // Открываем соединение
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // Строка, которая будет содержать ответ от сервера
             responseData = "";
            // Код ответа от сервера
            int responseCode;

            // Указываем тип запроса
            con.setRequestMethod("GET");

            responseCode = con.getResponseCode();
            System.out.println("nSending 'GET' request to URL : " + "https://www.google.com.ua/");
            System.out.println("Response Code : " + responseCode);

            // Открываем поток для приема данных от сервера
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            // Считываем входящие данные
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();

            // Преобразовываем StringBuffer в String
            responseData = response.toString();

            // Отображаем ответ от сервера в консоль
            System.out.println(responseData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // Класс AsyncTask предоставляет основной набор методов, для удобной работы с асинхронными потоками
    // Поток исполнения
    class WebTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        // Метод выполнения задачи в фоновом потоке
        // НЕ ИМЕЕТ ДОСТУПА К ОСНОВНОМУ ПОТОКУ ИСПОЛНЕНИЯ
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                googleRequset();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        // Метод выполняющийся перед методом doInBackground
        // ИМЕЮТ ДОСТУП К ОСНОВНОМУ ПОТОКУ ИСПОЛНЕНИЯ
        @Override
        protected void onPreExecute() {
            // Создаем диалоговое окно
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("Please wait");
            // Чтобы юзер не мог его закрыть
            progressDialog.setCancelable(false);

            progressDialog.show();

            super.onPreExecute();
        }

        // Метод выполняющийся после метода doInBackground
        // ИМЕЮТ ДОСТУП К ОСНОВНОМУ ПОТОКУ ИСПОЛНЕНИЯ
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tv.setText(responseData);
            progressDialog.cancel();
        }
    }
}
