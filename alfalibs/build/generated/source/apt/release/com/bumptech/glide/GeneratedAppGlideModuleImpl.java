package com.bumptech.glide;

import android.content.Context;
import android.util.Log;
import com.alfarabi.alfalibs.tools.MyGlideAppModule;
import java.lang.Class;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings("deprecation")
final class GeneratedAppGlideModuleImpl extends GeneratedAppGlideModule {
  private final MyGlideAppModule appGlideModule;

  GeneratedAppGlideModuleImpl() {
    appGlideModule = new MyGlideAppModule();
    if (Log.isLoggable("Glide", Log.DEBUG)) {
      Log.d("Glide", "Discovered AppGlideModule from annotation: com.alfarabi.alfalibs.tools.MyGlideAppModule");
    }
  }

  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    appGlideModule.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(Context context, Registry registry) {
    appGlideModule.registerComponents(context, registry);
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return appGlideModule.isManifestParsingEnabled();
  }

  @Override
  public Set<Class<?>> getExcludedModuleClasses() {
    return Collections.emptySet();
  }

  @Override
  GeneratedRequestManagerFactory getRequestManagerFactory() {
    return new GeneratedRequestManagerFactory();
  }
}
