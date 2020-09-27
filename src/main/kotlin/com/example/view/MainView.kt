package com.example.view

import com.example.core.SimplePermutationCipher
import javafx.scene.control.TextField
import java.io.File

import javafx.beans.binding.Bindings
import javafx.stage.FileChooser
import tornadofx.*
import java.io.FileNotFoundException


class MainView : View() {
    override val root = borderpane()

    private val ef = arrayOf(FileChooser.ExtensionFilter("Любой файл", "*.*"))


    private lateinit var filePath: TextField
    private var encodeBtn = button("Пуск") {
        action {
            SimplePermutationCipher.encodeFile(filePath.text)
        }
    }
    private var decodeBtn = button("Пуск") { action { SimplePermutationCipher.decodeFile(filePath.text) } }

    init {
        with(root) {
            title = "Шифр простой перестановки"
            center = form {
                fieldset("Выберите файл") {
                    hbox {
                        field("Путь до файла") {
                            hbox {
                                filePath = textfield()
                                encodeBtn.disableProperty().bind(Bindings.isEmpty(filePath.textProperty()))
                                decodeBtn.disableProperty().bind(Bindings.isEmpty(filePath.textProperty()))
                                button("Добавить") {
                                    action {
                                        val fn: List<File> =
                                            chooseFile("Выберите файл", ef, mode = FileChooserMode.Single)
                                        if (fn.isNotEmpty()) {
                                            filePath.text = "${fn.first()}"
                                        }
                                    }
                                }
                            }
                        }
                    }
                    field("Зашифровать") {
                        this += encodeBtn
                    }
                    field("Разшифровать") {
                        this += decodeBtn
                    }
                }
            }
        }
    }
}
