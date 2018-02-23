package com.bricksai.togo.utiles;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.zzf;


/**
 * Created by Remonda on 5/15/2017.
 */

public class MarkerMe {
        private final zzf zzbpB;

        public MarkerMe(zzf var1) {
            this.zzbpB = (zzf) zzac.zzw(var1);
        }

        public void remove() {
            try {
                this.zzbpB.remove();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public String getId() {
            try {
                return this.zzbpB.getId();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setPosition(@NonNull LatLng var1) {
            if(var1 == null) {
                throw new IllegalArgumentException("latlng cannot be null - a position is required.");
            } else {
                try {
                    this.zzbpB.setPosition(var1);
                } catch (RemoteException var3) {
                    throw new RuntimeRemoteException(var3);
                }
            }
        }

        public LatLng getPosition() {
            try {
                return this.zzbpB.getPosition();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setZIndex(float var1) {
            try {
                this.zzbpB.setZIndex(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public float getZIndex() {
            try {
                return this.zzbpB.getZIndex();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setIcon(@Nullable BitmapDescriptor var1) {
            try {
                if(var1 == null) {
                    this.zzbpB.zzM((IObjectWrapper)null);
                } else {
                    IObjectWrapper var2 = var1.zzJm();
                    this.zzbpB.zzM(var2);
                }

            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public void setAnchor(float var1, float var2) {
            try {
                this.zzbpB.setAnchor(var1, var2);
            } catch (RemoteException var4) {
                throw new RuntimeRemoteException(var4);
            }
        }

        public void setInfoWindowAnchor(float var1, float var2) {
            try {
                this.zzbpB.setInfoWindowAnchor(var1, var2);
            } catch (RemoteException var4) {
                throw new RuntimeRemoteException(var4);
            }
        }

        public void setTitle(@Nullable String var1) {
            try {
                this.zzbpB.setTitle(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }


        public String getTitle() {
            try {
                return this.zzbpB.getTitle();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setSnippet(@Nullable String var1) {
            try {
                this.zzbpB.setSnippet(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public String getSnippet() {
            try {
                return this.zzbpB.getSnippet();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setDraggable(boolean var1) {
            try {
                this.zzbpB.setDraggable(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public boolean isDraggable() {
            try {
                return this.zzbpB.isDraggable();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void showInfoWindow() {
            try {
                this.zzbpB.showInfoWindow();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void hideInfoWindow() {
            try {
                this.zzbpB.hideInfoWindow();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public boolean isInfoWindowShown() {
            try {
                return this.zzbpB.isInfoWindowShown();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setVisible(boolean var1) {
            try {
                this.zzbpB.setVisible(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public boolean isVisible() {
            try {
                return this.zzbpB.isVisible();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setFlat(boolean var1) {
            try {
                this.zzbpB.setFlat(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public boolean isFlat() {
            try {
                return this.zzbpB.isFlat();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setRotation(float var1) {
            try {
                this.zzbpB.setRotation(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public float getRotation() {
            try {
                return this.zzbpB.getRotation();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setAlpha(float var1) {
            try {
                this.zzbpB.setAlpha(var1);
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        public float getAlpha() {
            try {
                return this.zzbpB.getAlpha();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public void setTag(@Nullable Object var1) {
            try {
                this.zzbpB.setTag(zzd.zzA(var1));
            } catch (RemoteException var3) {
                throw new RuntimeRemoteException(var3);
            }
        }

        @Nullable
        public Object getTag() {
            try {
                return zzd.zzF(this.zzbpB.getTag());
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }

        public boolean equals(Object var1) {
            if(!(var1 instanceof MarkerMe)) {
                return false;
            } else {
                try {
                    return this.zzbpB.zzj(((MarkerMe)var1).zzbpB);
                } catch (RemoteException var3) {
                    throw new RuntimeRemoteException(var3);
                }
            }
        }

        public int hashCode() {
            try {
                return this.zzbpB.hashCodeRemote();
            } catch (RemoteException var2) {
                throw new RuntimeRemoteException(var2);
            }
        }
}
