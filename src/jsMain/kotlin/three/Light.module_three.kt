@file:JsModule("three")
@file:JsNonModule
@file:Suppress("ABSTRACT_MEMBER_NOT_IMPLEMENTED", "VAR_TYPE_MISMATCH_ON_OVERRIDE", "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "PackageDirectoryMismatch")
package three.js

import kotlin.js.*

open external class Light : Object3D {
    constructor(hex: Number = definedExternally, intensity: Number = definedExternally)
    constructor(hex: String = definedExternally, intensity: Number = definedExternally)
    override var type: String
    open var color: Color
    open var intensity: Number
    open var isLight: Boolean
    override var receiveShadow: Boolean
    open var shadow: LightShadow
    open var shadowCameraFov: Any
    open var shadowCameraLeft: Any
    open var shadowCameraRight: Any
    open var shadowCameraTop: Any
    open var shadowCameraBottom: Any
    open var shadowCameraNear: Any
    open var shadowCameraFar: Any
    open var shadowBias: Any
    open var shadowMapWidth: Any
    open var shadowMapHeight: Any
}