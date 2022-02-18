package com.example.industrialstructurecasestudy.dialogueBox

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import com.example.industrialstructurecasestudy.R

object YesNoDialogue{

    fun createSimpleYesNoDialog(context: Context,title: String, msg: String, listener : DialogInterface.OnClickListener ): Dialog {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Yes", listener)
            .setNegativeButton("Cancel", listener)
            .create()

        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun createInputYesNoDialog(
        context: Context,
        msg :  String,
        name: String,
        desc: String,
        listener : (orgNm : String, orgDesc : String) -> Unit

    ) : Dialog {

        val vw = LayoutInflater.from(context).inflate(R.layout.yes_no_input_dialogue_layout, null)
        vw.findViewById<EditText>(R.id.edtOrgName).setText(name)
        vw.findViewById<EditText>(R.id.edtOrgDec).setText(desc)



        val dialog = AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton("Yes") { _, _ ->
                val updNm = vw.findViewById<EditText>(R.id.edtOrgName).text.toString()
                val updDesc = vw.findViewById<EditText>(R.id.edtOrgDec).text.toString()
                Log.i("@ani", "In Dialog - $updNm, $updDesc")
                listener(updNm, updDesc)
            }
            .setNegativeButton("No") { di, _ -> di.dismiss()}
            .setView(vw)
            .create()

        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}