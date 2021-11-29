@file:OptIn(ExperimentalComposeWebWidgetsApi::class)
package pl.mareklangiewicz.kthreelhu

import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.common.material.Text
import org.jetbrains.compose.common.ui.ExperimentalComposeWebWidgetsApi
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.ui.Styles
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import pl.mareklangiewicz.widgets.CmnDText
import pl.mareklangiewicz.widgets.kim.KeyDownEffect
import pl.mareklangiewicz.widgets.kim.Kim
import pl.mareklangiewicz.widgets.kim.Kim.Companion.cmd
import pl.mareklangiewicz.widgets.kim.Kim.Companion.collect
import pl.mareklangiewicz.widgets.kim.Kim.Companion.toggledLocally
import pl.mareklangiewicz.widgets.kim.Kim.Key
import pl.mareklangiewicz.widgets.kim.MouseMoveEffect
import pl.mareklangiewicz.widgets.kim.MouseWheelEffect

fun main() {
    console.log("Kotlin version: ${KotlinVersion.CURRENT}")
    val root = document.getElementById("root") as HTMLElement
    renderComposable(root = root) { AppJs() }
}

@Composable fun AppJs() {
    Style(Styles)
    Kim.Area {
        Kim.KeyDownEffect(window)
        Kim.MouseMoveEffect(window)
        Kim.MouseWheelEffect(window)
        Key("q").cmd().collect { window.close() }
        Kim.Frame { AppContent() }
    }
}

@Composable private fun AppContent() {
    val scope = rememberCoroutineScope()
    var camPos by remember { mutableStateOf(XYZ(0.0, 0.0, 20.0)) }
    var camRot by remember { mutableStateOf(XYZ(0.0, 0.0, 0.0)) }

    Key("1").cmd().collect { scope.launch { threeExperiment1() } }
    Key("2").cmd().collect { scope.launch { threeExperiment2() } }

    val te by Key("e").toggledLocally(false)
    val tr by Key("r").toggledLocally(false)
    val tz by Key("z").toggledLocally(false)
    val ts by Key("s").toggledLocally(false)

    lateinit var mousePosBackup: XY
    lateinit var camPosBackup: XYZ
    lateinit var camRotBackup: XYZ
    var moving = false
    Key("c").cmd().collect { if (moving) { camPosBackup = camPos; camRotBackup = camRot } }
    Key("mmove").cmd().collect {
        val evt = it.data as MouseEvent
        val mousePos = evt.screenX.dbl xy evt.screenY.dbl
        if (!moving) mousePosBackup = mousePos
        val mousePosDelta = mousePos - mousePosBackup
        val factor = if (ts) 0.003 else 0.1
        when {
            !moving && (te || tr) -> {
                mousePosBackup = mousePos
                camPosBackup = camPos
                camRotBackup = camRot
                moving = true
            }
            te -> camPos = camPosBackup + mousePosDelta.to3d(tz) * factor
            tr -> camRot = camRotBackup + mousePosDelta.to3d(tz) * factor / 10.0
            moving -> { camPos = camPosBackup; camRot = camRotBackup; moving = false }
        }
    }


    H2 { Text("Kthreelhu JS") }
    Div {
        CmnDText("camPos:$camPos; camRot:$camRot; ts:$ts; tq:$te; tw:$tr tz:$tz", mono = true)
        KthExamples(camPos, camRot)
    }
}

private fun XY.to3d(swapYZ: Boolean = false) = if (swapYZ) x xy 0.0 yz y else x xy y yz 0.0

fun threeExperiment1() {
    console.log("TODO")
}

suspend fun threeExperiment2() {
    console.log("TODO")
//    val system = parseJsonBraxSystem("./walking_ant.json")
//    model?.scene?.addBraxSystem(system)
//    // TODO: trajectory/animation stuff (based on brax repo)
//    // TODO: review all brax stuff after something is moving..
}
