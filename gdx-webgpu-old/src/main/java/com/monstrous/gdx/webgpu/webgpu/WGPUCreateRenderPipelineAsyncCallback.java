
package com.monstrous.gdx.webgpu.webgpu;

/** NOTE: THIS FILE WAS PRE-GENERATED BY JNR_GEN! */
import jnr.ffi.Pointer;
import jnr.ffi.annotations.Delegate;

public interface WGPUCreateRenderPipelineAsyncCallback {

	@Delegate
	void invoke (WGPUCreatePipelineAsyncStatus status, Pointer pipeline, String message, Pointer userdata);
}
