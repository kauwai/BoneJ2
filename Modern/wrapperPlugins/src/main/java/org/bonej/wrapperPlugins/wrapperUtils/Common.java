/*
BSD 2-Clause License
Copyright (c) 2018, Michael Doube, Richard Domander, Alessandro Felder
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.
* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.bonej.wrapperPlugins.wrapperUtils;

import static org.scijava.ui.DialogPrompt.MessageType.WARNING_MESSAGE;
import static org.scijava.ui.DialogPrompt.OptionType.OK_CANCEL_OPTION;
import static org.scijava.ui.DialogPrompt.Result.OK_OPTION;

import net.imagej.ImgPlus;
import net.imagej.axis.CalibratedAxis;
import net.imagej.ops.OpEnvironment;
import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.ComplexType;

import org.bonej.utilities.ImagePlusUtil;
import org.scijava.ui.UIService;

import ij.ImagePlus;
import ij.process.LUT;

/**
 * Miscellaneous utility methods.
 *
 * @author Richard Domander
 * @author Michael Doube
 */
public final class Common {

	/**
	 * Generates the 'fire' look-up table (LUT) and returns it as an IJ1 LUT
	 * object
	 *
	 * @return fire LUT
	 */
	public static LUT makeFire() {
		final int[] r = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x04, 0x07, 0x0A, 0x0D,
			0x10, 0x13, 0x16, 0x19, 0x1C, 0x1F, 0x22, 0x25, 0x28, 0x2B, 0x2E, 0x31,
			0x34, 0x37, 0x3A, 0x3D, 0x40, 0x43, 0x46, 0x49, 0x4C, 0x4F, 0x52, 0x55,
			0x58, 0x5B, 0x5E, 0x62, 0x65, 0x68, 0x6B, 0x6E, 0x71, 0x74, 0x77, 0x7A,
			0x7D, 0x80, 0x83, 0x86, 0x89, 0x8C, 0x8F, 0x92, 0x94, 0x96, 0x98, 0x9A,
			0x9C, 0x9E, 0xA0, 0xA2, 0xA3, 0xA4, 0xA6, 0xA7, 0xA8, 0xAA, 0xAB, 0xAD,
			0xAE, 0xAF, 0xB1, 0xB2, 0xB3, 0xB5, 0xB6, 0xB8, 0xB9, 0xBA, 0xBC, 0xBD,
			0xBE, 0xC0, 0xC1, 0xC3, 0xC4, 0xC6, 0xC7, 0xC9, 0xCA, 0xCC, 0xCD, 0xCF,
			0xD0, 0xD1, 0xD2, 0xD4, 0xD5, 0xD6, 0xD7, 0xD9, 0xDA, 0xDC, 0xDD, 0xDF,
			0xE0, 0xE2, 0xE3, 0xE5, 0xE6, 0xE7, 0xE9, 0xEA, 0xEB, 0xED, 0xEE, 0xF0,
			0xF1, 0xF3, 0xF4, 0xF6, 0xF7, 0xF9, 0xFA, 0xFC, 0xFC, 0xFC, 0xFD, 0xFD,
			0xFD, 0xFE, 0xFE, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
		final int[] g = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x01, 0x03, 0x05, 0x07, 0x08, 0x0A, 0x0C, 0x0E,
			0x10, 0x13, 0x15, 0x18, 0x1B, 0x1D, 0x20, 0x23, 0x25, 0x28, 0x2B, 0x2E,
			0x30, 0x33, 0x36, 0x39, 0x3B, 0x3E, 0x41, 0x44, 0x46, 0x49, 0x4C, 0x4F,
			0x51, 0x54, 0x57, 0x5A, 0x5C, 0x5F, 0x62, 0x65, 0x67, 0x69, 0x6B, 0x6D,
			0x6F, 0x71, 0x73, 0x75, 0x77, 0x79, 0x7B, 0x7D, 0x7F, 0x81, 0x83, 0x85,
			0x86, 0x88, 0x8A, 0x8C, 0x8D, 0x8F, 0x91, 0x93, 0x94, 0x96, 0x98, 0x9A,
			0x9B, 0x9D, 0x9F, 0xA1, 0xA2, 0xA4, 0xA6, 0xA8, 0xA9, 0xAB, 0xAD, 0xAF,
			0xB0, 0xB2, 0xB4, 0xB6, 0xB8, 0xBA, 0xBC, 0xBE, 0xBF, 0xC1, 0xC3, 0xC5,
			0xC7, 0xC9, 0xCB, 0xCD, 0xCE, 0xD0, 0xD2, 0xD4, 0xD5, 0xD7, 0xD9, 0xDB,
			0xDC, 0xDE, 0xE0, 0xE2, 0xE4, 0xE6, 0xE8, 0xEA, 0xEB, 0xED, 0xEF, 0xF1,
			0xF2, 0xF4, 0xF6, 0xF8, 0xF8, 0xF9, 0xFA, 0xFB, 0xFC, 0xFD, 0xFE, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
		final int[] b = { 0x00, 0x07, 0x0F, 0x16, 0x1E, 0x26, 0x2D, 0x35, 0x3D,
			0x41, 0x45, 0x4A, 0x4E, 0x52, 0x57, 0x5B, 0x60, 0x64, 0x68, 0x6C, 0x71,
			0x75, 0x79, 0x7D, 0x82, 0x86, 0x8A, 0x8F, 0x93, 0x97, 0x9C, 0xA0, 0xA5,
			0xA8, 0xAB, 0xAF, 0xB2, 0xB5, 0xB9, 0xBC, 0xC0, 0xC3, 0xC7, 0xCA, 0xCE,
			0xD1, 0xD5, 0xD8, 0xDC, 0xDC, 0xDD, 0xDE, 0xDF, 0xE0, 0xE1, 0xE2, 0xE3,
			0xE0, 0xDE, 0xDC, 0xDA, 0xD8, 0xD6, 0xD4, 0xD2, 0xCE, 0xCA, 0xC7, 0xC3,
			0xBF, 0xBC, 0xB8, 0xB5, 0xB1, 0xAD, 0xA9, 0xA6, 0xA2, 0x9E, 0x9A, 0x97,
			0x93, 0x8F, 0x8C, 0x88, 0x84, 0x81, 0x7D, 0x7A, 0x76, 0x72, 0x6F, 0x6B,
			0x67, 0x64, 0x60, 0x5D, 0x59, 0x55, 0x52, 0x4E, 0x4A, 0x47, 0x43, 0x40,
			0x3C, 0x38, 0x35, 0x31, 0x2D, 0x2A, 0x26, 0x23, 0x1F, 0x1B, 0x17, 0x14,
			0x10, 0x0C, 0x08, 0x05, 0x04, 0x03, 0x03, 0x02, 0x01, 0x01, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x08, 0x0D, 0x11,
			0x15, 0x1A, 0x1E, 0x23, 0x2A, 0x32, 0x3A, 0x42, 0x4A, 0x52, 0x5A, 0x62,
			0x69, 0x71, 0x79, 0x81, 0x88, 0x90, 0x98, 0xA0, 0xA7, 0xAF, 0xB7, 0xBF,
			0xC7, 0xCF, 0xD7, 0xDF, 0xE3, 0xE7, 0xEB, 0xEF, 0xF3, 0xF7, 0xFB, 0xFF,
			0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
		final byte[] reds = new byte[r.length];
		final byte[] greens = new byte[r.length];
		final byte[] blues = new byte[r.length];
		for (int i = 0; i < r.length; i++) {
			reds[i] = (byte) r[i];
			greens[i] = (byte) g[i];
			blues[i] = (byte) b[i];
		}
		return new LUT(8, 256, reds, greens, blues);
	}

	/**
	 * Converts the {@link ImgPlus} to a new ImgPlus with {@link BitType}
	 * elements.
	 * <p>
	 * Also copies ImgPlus metadata.
	 * </p>
	 *
	 * @param ops an {@link OpService} to find the necessary ops for conversion.
	 * @param imgPlus an image.
	 * @param <C> type of the elements in the input image.
	 * @return the image converted to bit type.
	 */
	public static <C extends ComplexType<C>> ImgPlus<BitType> toBitTypeImgPlus(
		final OpEnvironment ops, final ImgPlus<C> imgPlus)
	{
		final Img<BitType> convertedImg = ops.convert().bit(imgPlus.getImg());
		final ImgPlus<BitType> convertedImgPlus = new ImgPlus<>(convertedImg);
		copyMetadata(imgPlus, convertedImgPlus);

		return convertedImgPlus;
	}

	/**
	 * Shows a warning dialog about image anisotropy, and asks if the user wants
	 * to continue.
	 *
	 * @param image the current image open in ImageJ.
	 * @param uiService used to display the warning dialog.
	 * @return true if user chose OK_OPTION, or image is not anisotropic. False if
	 *         user chose 'cancel' or they closed the dialog.
	 */
	public static boolean warnAnisotropy(final ImagePlus image,
		final UIService uiService)
	{
		final double anisotropy = ImagePlusUtil.anisotropy(image);
		if (anisotropy < 1E-3) {
			return true;
		}
		final String anisotropyPercent = String.format("(%.1f %%)", anisotropy *
			100.0);
		return uiService.showDialog("The image is anisotropic " +
			anisotropyPercent + ". Continue anyway?", WARNING_MESSAGE,
			OK_CANCEL_OPTION) == OK_OPTION;
	}

	/**
	 * Copies image metadata such as name, axis types and calibrations from source
	 * to target.
	 *
	 * @param source source of metadata.
	 * @param target target of metadata.
	 */
	private static void copyMetadata(final ImgPlus<?> source,
		final ImgPlus<?> target)
	{
		target.setName(source.getName());

		final int dimensions = source.numDimensions();
		for (int d = 0; d < dimensions; d++) {
			final CalibratedAxis axis = source.axis(d);
			target.setAxis(axis, d);
		}
	}
}
