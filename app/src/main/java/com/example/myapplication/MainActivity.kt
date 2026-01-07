package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ---------- ЗАВДАННЯ 1 ----------

        val editHP = findViewById<EditText>(R.id.editHP)
        val editCP = findViewById<EditText>(R.id.editCP)
        val editSP = findViewById<EditText>(R.id.editSP)
        val editNP = findViewById<EditText>(R.id.editNP)
        val editOP = findViewById<EditText>(R.id.editOP)
        val editWP = findViewById<EditText>(R.id.editWP)
        val editAP = findViewById<EditText>(R.id.editAP)
        val buttonCalc1 = findViewById<Button>(R.id.buttonCalc1)
        val textResult1 = findViewById<TextView>(R.id.textResult1)

        buttonCalc1.setOnClickListener {
            try {
                val H = parse(editHP)
                val C = parse(editCP)
                val S = parse(editSP)
                val N = parse(editNP)
                val O = parse(editOP)
                val W = parse(editWP)
                val A = parse(editAP)

                // коэффициенты перехода (табл. 1.1)
                val kPc = 100.0 / (100.0 - W)
                val kPg = 100.0 / (100.0 - W - A)

                // сухая маса
                val Hd = H * kPc
                val Cd = C * kPc
                val Sd = S * kPc
                val Nd = N * kPc
                val Od = O * kPc
                val Ad = A * kPc

                // горюча маса
                val Hf = H * kPg
                val Cf = C * kPg
                val Sf = S * kPg
                val Nf = N * kPg
                val Of = O * kPg

                //  формула Мендєлєєва
                val Qr_kJkg =
                    339.0 * C + 1030.0 * H - 108.8 * (O - S) - 25.0 * W
                val Qr = Qr_kJkg / 1000.0
                val Qd = Qr * 100.0 / (100.0 - W)   // на суху масу
                val Qdaf = Qr * 100.0 / (100.0 - W - A) // на горючу масу

                val text = buildString {
                    appendLine("Коеф. переходу:")
                    appendLine("K_PC = ${fmt(kPc)},  K_PG = ${fmt(kPg)}")
                    appendLine()
                    appendLine("Суха маса (%):")
                    appendLine(
                        "Hc=${fmt(Hd)}; Cc=${fmt(Cd)}; Sc=${fmt(Sd)}; " +
                                "Nc=${fmt(Nd)}; Oc=${fmt(Od)}; Ac=${fmt(Ad)}"
                    )
                    appendLine()
                    appendLine("Горюча маса (%):")
                    appendLine(
                        "Hg=${fmt(Hf)}; Cg=${fmt(Cf)}; Sg=${fmt(Sf)}; " +
                                "Ng=${fmt(Nf)}; Og=${fmt(Of)}"
                    )
                    appendLine()
                    appendLine("Нижча теплота згоряння:")
                    appendLine("Qr (робоча) = ${fmt(Qr)} МДж/кг")
                    appendLine("Qd (суха)   = ${fmt(Qd)} МДж/кг")
                    appendLine("Qdaf (горюча) = ${fmt(Qdaf)} МДж/кг")
                }

                textResult1.text = text

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Перевірте введені дані (завдання 1)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // ---------- ЗАВДАННЯ 2 ----------

        val editC2 = findViewById<EditText>(R.id.editC2)
        val editH2 = findViewById<EditText>(R.id.editH2)
        val editO2 = findViewById<EditText>(R.id.editO2)
        val editS2 = findViewById<EditText>(R.id.editS2)
        val editQdaf2 = findViewById<EditText>(R.id.editQdaf2)
        val editWr2 = findViewById<EditText>(R.id.editWr2)
        val editAd2 = findViewById<EditText>(R.id.editAd2)
        val editVdaf2 = findViewById<EditText>(R.id.editVdaf2)
        val buttonCalc2 = findViewById<Button>(R.id.buttonCalc2)
        val textResult2 = findViewById<TextView>(R.id.textResult2)

        buttonCalc2.setOnClickListener {
            try {
                val Cg = parse(editC2)
                val Hg = parse(editH2)
                val Og = parse(editO2)
                val Sg = parse(editS2)
                val Qdaf = parse(editQdaf2)  // МДж/кг
                val Wr = parse(editWr2)
                val Ad = parse(editAd2)
                val Vdaf = parse(editVdaf2)  // мг/кг

                val kComp = (100.0 - Wr - Ad) / 100.0
                val Cr = Cg * kComp
                val Hr = Hg * kComp
                val Or = Og * kComp
                val Sr = Sg * kComp


                val Ar = Ad * (100.0 - Wr) / 100.0


                val Vr = Vdaf * (100.0 - Wr) / 100.0


                val Qr2 = Qdaf * (100.0 - Wr - Ar) / 100.0

                val text = buildString {
                    appendLine("Склад робочої маси мазуту (%):")
                    appendLine(
                        "Cᵣ=${fmt(Cr)}; Hᵣ=${fmt(Hr)}; Oᵣ=${fmt(Or)}; " +
                                "Sᵣ=${fmt(Sr)}; Aᵣ=${fmt(Ar)};"
                    )
                    appendLine("Vᵣ=${fmt(Vr)} мг/кг")
                    appendLine()
                    appendLine("Нижча теплота згоряння на робочу масу:")
                    appendLine("Qr = ${fmt(Qr2)} МДж/кг")
                }

                textResult2.text = text

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Перевірте введені дані (завдання 2)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun parse(editText: EditText): Double {
        val s = editText.text.toString().replace(',', '.').trim()
        return s.toDouble()
    }

    private fun fmt(value: Double): String {
        return String.format("%.2f", value)
    }
}
