package io.piotrjastrzebski.playground.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.DelaunayTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;
import io.piotrjastrzebski.playground.BaseScreen;
import io.piotrjastrzebski.playground.GameReset;
import io.piotrjastrzebski.playground.PlaygroundGame;
import net.dermetfan.gdx.math.BayazitDecomposer;

import java.util.Arrays;

/**
 * Created by EvilEntity on 25/01/2016.
 */
public class XMLPathTest extends BaseScreen {
	private static final String TAG = XMLPathTest.class.getSimpleName();

	String xmlBox = "M 0,952.36218 L 100,952.36218 L 100,1052.3622 L 0,1052.3622 Z";
	String xmlInner = "M 66.31743,1052.7874 L 64.629158,1049.7911 L 64.001728,1046.7152 L 64.893835,1040.3964 L 66.922627,1033.9748 L 68.01698,1027.5941 L 67.51063,1024.2672 L 66.179886,1021.7339 L 64.194184,1019.8853 L 61.722963,1018.6122 L 56.001716,1017.3571 L 50.37165,1017.0968 L 44.542746,1017.284 L 38.578245,1018.52 L 35.994292,1019.7954 L 33.915414,1021.6499 L 32.521271,1024.1891 L 31.99152,1027.5187 L 33.085882,1033.8994 L 35.114672,1040.3211 L 36.006774,1046.6398 L 35.379343,1049.7157 L 33.69107,1052.712 L 30.728971,1054.6355 L 26.201547,1055.5058 L 14.867496,1055.0691 L 0,1052.3622 L 2.706905,1037.4947 L 3.1436775,1026.1606 L 2.2733933,1021.6332 L 0.3499,1018.6711 L -2.6463682,1016.9828 L -5.7222662,1016.3554 L -12.041063,1017.2475 L -18.46271,1019.2763 L -24.84343,1020.3706 L -28.172993,1019.8409 L -30.712149,1018.4467 L -32.566553,1016.3679 L -33.841862,1013.7839 L -35.077822,1007.8194 L -35.26528,1001.9905 L -35.005005,996.36044 L -33.749919,990.63918 L -32.47688,988.16794 L -30.628209,986.18223 L -28.094927,984.85146 L -24.76806,984.3451 L -18.387336,985.4396 L -11.965688,987.46841 L -5.6468906,988.36046 L -2.5709911,987.733 L 0.42528,986.0447 L 2.34877,983.08259 L 3.2190517,978.55516 L 2.7822762,967.2211 L 0.07537,952.3536 L 14.942868,949.64669 L 26.276921,949.20991 L 30.804348,950.0802 L 33.76645,952.0037 L 35.454722,954.99997 L 36.08215,958.07587 L 35.19004,964.39467 L 33.161245,970.81634 L 32.06689,977.1971 L 32.596644,980.52666 L 33.990789,983.06583 L 36.069667,984.92025 L 38.65362,986.19557 L 44.618121,987.43153 L 50.44703,987.6189 L 56.077091,987.35875 L 61.798339,986.10365 L 64.269561,984.83058 L 66.255264,982.98187 L 67.58601,980.44857 L 68.09236,977.1217 L 66.997997,970.74095 L 64.969202,964.31929 L 64.077097,958.00051 L 64.704528,954.92464 L 66.3928,951.9284 L 69.351661,950.00814 L 73.870548,949.14638 L 85.178694,949.60904 L 100.00851,952.3536 L 97.26391,967.2588 L 96.801222,978.61878 L 97.662966,983.15475 L 99.58322,986.1201 L 102.57949,987.80836 L 105.65539,988.43578 L 111.97419,987.54366 L 118.39584,985.51486 L 124.77656,984.4205 L 128.10342,984.92686 L 130.6367,986.25762 L 132.48537,988.24334 L 133.7584,990.71456 L 135.01349,996.4358 L 135.27378,1002.0658 L 135.08633,1007.8947 L 133.85037,1013.8592 L 132.57506,1016.4432 L 130.72065,1018.5221 L 128.18149,1019.9162 L 124.85193,1020.446 L 118.47121,1019.3515 L 112.04956,1017.3227 L 105.73077,1016.4307 L 102.65487,1017.0581 L 99.6586,1018.7464 L 97.735107,1021.7085 L 96.864823,1026.2359 L 97.301595,1037.57 L 100.0085,1052.4375 L 85.141004,1055.1444 L 73.806953,1055.5812 L 69.279529,1054.7109 L 66.31743,1052.7874 L 66.31743,1052.7874";
	String xmlEdgeOIO = "M 0.34991,66.3089 L -2.646361,64.6206 L -5.722261,63.9932 L -12.041057,64.8853 L -18.462705,66.9141 L -24.843429,68.0085 L -28.17299,67.4787 L -30.712144,66.0846 L -32.566547,64.0057 L -33.841857,61.4217 L -35.077816,55.4572 L -35.265269,49.628304 L -35.004994,43.998254 L -33.749908,38.277044 L -32.476869,35.805834 L -30.628198,33.820154 L -28.094916,32.489434 L -24.768049,31.983104 L -18.387329,33.077584 L -11.965682,35.106324 L -5.646886,35.998344 L -2.570988,35.370884 L 0.42528,33.682604 L 2.345534,30.723764 L 3.207279,26.204884 L 2.744595,14.896744 L 0,0.06690484 L 99.933136,0.06690484 L 97.226231,14.934394 L 96.789451,26.268424 L 97.659741,30.795824 L 99.583226,33.757904 L 102.57951,35.446194 L 105.65541,36.073644 L 111.9742,35.181554 L 118.39585,33.152764 L 124.77657,32.058404 L 128.10344,32.564764 L 130.63671,33.895514 L 132.48538,35.881214 L 133.75842,38.352424 L 135.0135,44.073654 L 135.27379,49.703704 L 135.08634,55.5326 L 133.85038,61.4971 L 132.57507,64.0811 L 130.72067,66.16 L 128.18151,67.5541 L 124.85195,68.0839 L 118.47123,66.9894 L 112.04958,64.9606 L 105.73078,64.0686 L 102.65488,64.696 L 99.658596,66.3843 L 97.735111,69.3464 L 96.864831,73.8738 L 97.301601,85.2079 L 100.00852,100.0754 L 85.141011,102.7823 L 73.806961,103.2191 L 69.279531,102.3488 L 66.317431,100.4253 L 64.629164,97.429 L 64.001734,94.3531 L 64.893841,88.0343 L 66.922641,81.6127 L 68.016991,75.232 L 67.510641,71.9051 L 66.179891,69.3719 L 64.194194,67.5232 L 61.722974,66.2502 L 56.001724,64.9951 L 50.371661,64.7348 L 44.542752,64.922 L 38.578251,66.158 L 35.994298,67.4333 L 33.91542,69.2878 L 32.521275,71.827 L 31.991521,75.1566 L 33.085884,81.5373 L 35.114678,87.9589 L 36.006784,94.2777 L 35.379353,97.3536 L 33.691081,100.3499 L 30.728981,102.2734 L 26.201556,103.1437 L 14.867502,102.7069 L 0,100 L 2.70691,85.1325 L 3.143686,73.7984 L 2.273403,69.271 L 0.34991,66.3089 L 0.34991,66.3089";
	String xmlEdgeIOI = "M 100.28306,66.3842 L 97.286781,64.6959 L 94.210881,64.0685 L 87.892081,64.9606 L 81.470431,66.9893 L 75.089711,68.0837 L 71.760151,67.554 L 69.221001,66.1598 L 67.366591,64.081 L 66.091281,61.497 L 64.855321,55.5325 L 64.667874,49.703604 L 64.928141,44.073504 L 66.183231,38.352244 L 67.456271,35.881024 L 69.304941,33.895314 L 71.838221,32.564564 L 75.165091,32.058204 L 81.545811,33.152694 L 87.967461,35.181474 L 94.286251,36.073524 L 97.362151,35.446074 L 100.35843,33.757804 L 102.28192,30.795694 L 103.15221,26.268264 L 102.71543,14.934204 L 100.00853,0.06670484 L 1e-5,-0.00829516 L -2.706896,14.859154 L -3.143672,26.193184 L -2.27339,30.720604 L -0.34989996,33.682704 L 2.646371,35.370964 L 5.722271,35.998384 L 12.041069,35.106264 L 18.462717,33.077464 L 24.843441,31.983104 L 28.170304,32.489464 L 30.70358,33.820214 L 32.552246,35.805924 L 33.825281,38.277144 L 35.080367,43.998404 L 35.340661,49.628504 L 35.153207,55.4574 L 33.917247,61.4219 L 32.641937,64.0059 L 30.787531,66.0847 L 28.248375,67.4789 L 24.918811,68.0086 L 18.538091,66.9141 L 12.116444,64.8853 L 5.797646,63.9933 L 2.721748,64.6208 L -0.27451996,66.3091 L -2.201252,69.2712 L -3.080076,73.7986 L -2.669215,85.1326 L 0,100 L 14.829817,97.2931 L 26.137963,96.8564 L 30.65685,97.7266 L 33.615711,99.6501 L 35.303983,102.6464 L 35.931413,105.7223 L 35.039306,112.0411 L 33.010514,118.4628 L 31.916161,124.8435 L 32.422508,128.1704 L 33.753251,130.7036 L 35.738952,132.5523 L 38.210173,133.8253 L 43.93142,135.0804 L 49.561481,135.3407 L 55.390394,135.1535 L 61.354894,133.9175 L 63.938844,132.6421 L 66.017721,130.7876 L 67.411871,128.2484 L 67.941621,124.9188 L 66.847261,118.5381 L 64.818471,112.1165 L 63.926364,105.7977 L 64.553794,102.7218 L 66.242071,99.7255 L 69.204171,97.802 L 73.731591,96.9317 L 85.065641,97.3685 L 99.933136,100.0754 L 102.64006,85.2079 L 103.07684,73.8739 L 102.20655,69.3465 L 100.28306,66.3844 L 100.28306,66.3842";
	String xmlCorner = "M 33.69108,1052.7117 L 35.379352,1049.7154 L 36.00678,1046.6395 L 35.11467,1040.3207 L 33.085875,1033.8991 L 31.99152,1027.5184 L 32.521274,1024.1888 L 33.915419,1021.6496 L 35.994297,1019.7952 L 38.57825,1018.5199 L 44.542751,1017.284 L 50.37166,1017.0965 L 56.001721,1017.3566 L 61.722968,1018.6118 L 64.194189,1019.8848 L 66.17989,1021.7335 L 67.510633,1024.2668 L 68.01698,1027.5937 L 66.922618,1033.9744 L 64.893827,1040.3961 L 64.001726,1046.7149 L 64.629157,1049.7908 L 66.31743,1052.7871 L 69.27953,1054.7106 L 73.806956,1055.5808 L 85.141014,1055.1441 L 100.00852,1052.4372 L 97.301611,1037.5697 L 96.864836,1026.2356 L 97.73512,1021.7082 L 99.658615,1018.7461 L 102.65488,1017.0578 L 105.73078,1016.4304 L 112.04958,1017.3225 L 118.47123,1019.3513 L 124.85195,1020.4457 L 128.18151,1019.9159 L 130.72066,1018.5218 L 132.57507,1016.4429 L 133.85038,1013.8589 L 135.08634,1007.8944 L 135.27379,1002.0655 L 135.01352,996.43543 L 133.75843,990.71417 L 132.4854,988.24295 L 130.63673,986.25725 L 128.10345,984.92651 L 124.77658,984.42018 L 118.39586,985.51465 L 111.97421,987.5434 L 105.65541,988.43542 L 102.57951,987.80796 L 99.583235,986.11968 L 97.659744,983.15757 L 96.789461,978.63014 L 97.226237,967.29608 L 99.933145,952.42858 L 0,952.35358 L 0,1052.3622 L 14.867498,1055.0691 L 26.201551,1055.5058 L 30.728978,1054.6355 L 33.69108,1052.712 L 33.69108,1052.7117";

	String xmlBox2 = "M 0,-1.516e-5 L 100.00001,-1.516e-5 L 100.00001,100 L 0,100 Z";
	String xmlInner2 = "M 66.317431,100.4252 L 64.629164,97.4289 L 64.001734,94.353 L 64.893831,88.0342 L 66.922631,81.6126 L 68.016981,75.2319 L 67.510631,71.905 L 66.179891,69.3717 L 64.194184,67.5231 L 61.722964,66.25 L 56.001714,64.9949 L 50.371651,64.7346 L 44.542747,64.9218 L 38.578246,66.1578 L 35.994293,67.4332 L 33.915415,69.2877 L 32.521272,71.8269 L 31.991521,75.1565 L 33.085883,81.5372 L 35.114673,87.9589 L 36.006775,94.2776 L 35.379344,97.3535 L 33.691071,100.3498 L 30.728972,102.2733 L 26.201548,103.1436 L 14.867497,102.7069 L 0,100 L 2.706905,85.1325 L 3.143677,73.7984 L 2.273393,69.271 L 0.3499,66.3089 L -2.646368,64.6206 L -5.722266,63.9932 L -12.041062,64.8853 L -18.462709,66.9141 L -24.843429,68.0084 L -28.172992,67.4787 L -30.712148,66.0845 L -32.566552,64.0057 L -33.841861,61.4217 L -35.077821,55.4572 L -35.265279,49.628304 L -35.005004,43.998244 L -33.749918,38.276984 L -32.476879,35.805744 L -30.628208,33.820034 L -28.094926,32.489264 L -24.768059,31.982904 L -18.387335,33.077404 L -11.965687,35.106214 L -5.646891,35.998264 L -2.570991,35.370804 L 0.42528,33.682504 L 2.34877,30.720394 L 3.219052,26.192964 L 2.782276,14.858904 L 0.07537,-0.00859516 L 14.942869,-2.7155052 L 26.276922,-3.1522852 L 30.804349,-2.2819952 L 33.766451,-0.3584952 L 35.454723,2.6377748 L 36.082151,5.7136748 L 35.190041,12.032474 L 33.161246,18.454144 L 32.066891,24.834904 L 32.596645,28.164464 L 33.99079,30.703634 L 36.069668,32.558054 L 38.653621,33.833374 L 44.618122,35.069334 L 50.447031,35.256704 L 56.077094,34.996554 L 61.798344,33.741454 L 64.269564,32.468384 L 66.255261,30.619674 L 67.586011,28.086374 L 68.092361,24.759504 L 66.998001,18.378754 L 64.969201,11.957094 L 64.077094,5.6383148 L 64.704534,2.5624448 L 66.392801,-0.4337952 L 69.351661,-2.3540552 L 73.870551,-3.2158152 L 85.178691,-2.7531552 L 100.00852,-0.00859516 L 97.263911,14.896604 L 96.801221,26.256584 L 97.662971,30.792554 L 99.583216,33.757904 L 102.5795,35.446164 L 105.6554,36.073584 L 111.9742,35.181464 L 118.39585,33.152664 L 124.77657,32.058304 L 128.10343,32.564664 L 130.63671,33.895424 L 132.48538,35.881144 L 133.75841,38.352364 L 135.0135,44.073604 L 135.27379,49.703604 L 135.08634,55.5325 L 133.85038,61.497 L 132.57507,64.081 L 130.72066,66.1599 L 128.1815,67.554 L 124.85194,68.0838 L 118.47122,66.9893 L 112.04957,64.9605 L 105.73078,64.0685 L 102.65488,64.6959 L 99.658596,66.3842 L 97.735111,69.3463 L 96.864821,73.8737 L 97.301591,85.2078 L 100.00851,100.0753 L 85.141001,102.7822 L 73.806951,103.219 L 69.279531,102.3487 L 66.317431,100.4252 L 66.317431,100.4252";
	String xmlEdgeIOI2 = "M 100.28306,66.3842 L 97.286781,64.6959 L 94.210881,64.0685 L 87.892081,64.9606 L 81.470431,66.9893 L 75.089711,68.0837 L 71.760151,67.554 L 69.221001,66.1598 L 67.366591,64.081 L 66.091281,61.497 L 64.855321,55.5325 L 64.667874,49.703604 L 64.928141,44.073504 L 66.183231,38.352244 L 67.456271,35.881024 L 69.304941,33.895314 L 71.838221,32.564564 L 75.165091,32.058204 L 81.545811,33.152694 L 87.967461,35.181474 L 94.286251,36.073524 L 97.362151,35.446074 L 100.35843,33.757804 L 102.28192,30.795694 L 103.15221,26.268264 L 102.71543,14.934204 L 100.00853,0.06670484 L 1e-5,-0.00829516 L -2.706896,14.859154 L -3.143672,26.193184 L -2.27339,30.720604 L -0.34989996,33.682704 L 2.646371,35.370964 L 5.722271,35.998384 L 12.041069,35.106264 L 18.462717,33.077464 L 24.843441,31.983104 L 28.170304,32.489464 L 30.70358,33.820214 L 32.552246,35.805924 L 33.825281,38.277144 L 35.080367,43.998404 L 35.340661,49.628504 L 35.153207,55.4574 L 33.917247,61.4219 L 32.641937,64.0059 L 30.787531,66.0847 L 28.248375,67.4789 L 24.918811,68.0086 L 18.538091,66.9141 L 12.116444,64.8853 L 5.797646,63.9933 L 2.721748,64.6208 L -0.27451996,66.3091 L -2.201252,69.2712 L -3.080076,73.7986 L -2.669215,85.1326 L 0,100 L 14.829817,97.2931 L 26.137963,96.8564 L 30.65685,97.7266 L 33.615711,99.6501 L 35.303983,102.6464 L 35.931413,105.7223 L 35.039306,112.0411 L 33.010514,118.4628 L 31.916161,124.8435 L 32.422508,128.1704 L 33.753251,130.7036 L 35.738952,132.5523 L 38.210173,133.8253 L 43.93142,135.0804 L 49.561481,135.3407 L 55.390394,135.1535 L 61.354894,133.9175 L 63.938844,132.6421 L 66.017721,130.7876 L 67.411871,128.2484 L 67.941621,124.9188 L 66.847261,118.5381 L 64.818471,112.1165 L 63.926364,105.7977 L 64.553794,102.7218 L 66.242071,99.7255 L 69.204171,97.802 L 73.731591,96.9317 L 85.065641,97.3685 L 99.933136,100.0754 L 102.64006,85.2079 L 103.07684,73.8739 L 102.20655,69.3465 L 100.28306,66.3844 L 100.28306,66.3842";
	String xmlEdgeOIO2 = "M 0.34991,66.3089 L -2.646361,64.6206 L -5.722261,63.9932 L -12.041057,64.8853 L -18.462705,66.9141 L -24.843429,68.0085 L -28.17299,67.4787 L -30.712144,66.0846 L -32.566547,64.0057 L -33.841857,61.4217 L -35.077816,55.4572 L -35.265269,49.628304 L -35.004994,43.998254 L -33.749908,38.277044 L -32.476869,35.805834 L -30.628198,33.820154 L -28.094916,32.489434 L -24.768049,31.983104 L -18.387329,33.077584 L -11.965682,35.106324 L -5.646886,35.998344 L -2.570988,35.370884 L 0.42528,33.682604 L 2.345534,30.723764 L 3.207279,26.204884 L 2.744595,14.896744 L 0,0.06690484 L 99.933136,0.06690484 L 97.226231,14.934394 L 96.789451,26.268424 L 97.659741,30.795824 L 99.583226,33.757904 L 102.57951,35.446194 L 105.65541,36.073644 L 111.9742,35.181554 L 118.39585,33.152764 L 124.77657,32.058404 L 128.10344,32.564764 L 130.63671,33.895514 L 132.48538,35.881214 L 133.75842,38.352424 L 135.0135,44.073654 L 135.27379,49.703704 L 135.08634,55.5326 L 133.85038,61.4971 L 132.57507,64.0811 L 130.72067,66.16 L 128.18151,67.5541 L 124.85195,68.0839 L 118.47123,66.9894 L 112.04958,64.9606 L 105.73078,64.0686 L 102.65488,64.696 L 99.658596,66.3843 L 97.735111,69.3464 L 96.864831,73.8738 L 97.301601,85.2079 L 100.00852,100.0754 L 85.141011,102.7823 L 73.806961,103.2191 L 69.279531,102.3488 L 66.317431,100.4253 L 64.629164,97.429 L 64.001734,94.3531 L 64.893841,88.0343 L 66.922641,81.6127 L 68.016991,75.232 L 67.510641,71.9051 L 66.179891,69.3719 L 64.194194,67.5232 L 61.722974,66.2502 L 56.001724,64.9951 L 50.371661,64.7348 L 44.542752,64.922 L 38.578251,66.158 L 35.994298,67.4333 L 33.91542,69.2878 L 32.521275,71.827 L 31.991521,75.1566 L 33.085884,81.5373 L 35.114678,87.9589 L 36.006784,94.2777 L 35.379353,97.3536 L 33.691081,100.3499 L 30.728981,102.2734 L 26.201556,103.1437 L 14.867502,102.7069 L 0,100 L 2.70691,85.1325 L 3.143686,73.7984 L 2.273403,69.271 L 0.34991,66.3089 L 0.34991,66.3089";
	String xmlCorner2 = "M 33.691081,100.3495 L 35.379353,97.3532 L 36.006781,94.2773 L 35.114671,87.9585 L 33.085876,81.5369 L 31.991521,75.1562 L 32.521275,71.8266 L 33.91542,69.2874 L 35.994298,67.433 L 38.578251,66.1577 L 44.542752,64.9218 L 50.371661,64.7343 L 56.001724,64.9944 L 61.722974,66.2496 L 64.194194,67.5226 L 66.179891,69.3713 L 67.510631,71.9046 L 68.016981,75.2315 L 66.922621,81.6122 L 64.893831,88.0339 L 64.001724,94.3527 L 64.629154,97.4286 L 66.317431,100.4249 L 69.279531,102.3484 L 73.806961,103.2186 L 85.141011,102.7819 L 100.00853,100.075 L 97.301611,85.2075 L 96.864841,73.8734 L 97.735121,69.346 L 99.658606,66.3839 L 102.65489,64.6956 L 105.73079,64.0682 L 112.04959,64.9603 L 118.47124,66.9891 L 124.85196,68.0835 L 128.18152,67.5537 L 130.72067,66.1596 L 132.57508,64.0807 L 133.85039,61.4967 L 135.08635,55.5322 L 135.2738,49.703304 L 135.01353,44.073234 L 133.75844,38.351974 L 132.48541,35.880754 L 130.63674,33.895054 L 128.10346,32.564314 L 124.77659,32.057984 L 118.39587,33.152454 L 111.97422,35.181204 L 105.65542,36.073224 L 102.57952,35.445764 L 99.583226,33.757484 L 97.659741,30.795374 L 96.789461,26.267944 L 97.226241,14.933884 L 99.933136,0.06638484 L 0,-0.00861516 L 0,100 L 14.867499,102.7069 L 26.201552,103.1436 L 30.728979,102.2733 L 33.691081,100.3498 L 33.691081,100.3495";

	String xmlInner3 = "M 90.83974,-1.13887 L 81.45331,-3.01594 L 72.73228,-3.3219 L 68.89991,-2.16406 L 65.56821,0.25254 L 64.06045,2.62949 L 63.54977,5.39453 L 64.53819,11.75746 L 66.57065,18.67852 L 67.68433,25.49492 L 66.3274,30.08874 L 62.59216,33.0107 L 56.98193,34.55105 L 50,35 L 43.01808,34.55105 L 37.40784,33.0107 L 33.6726,30.08874 L 32.31567,25.49492 L 33.42935,18.67852 L 35.46181,11.75746 L 36.45024,5.39453 L 35.93955,2.62949 L 34.43179,0.25254 L 31.1001,-2.16406 L 27.26772,-3.3219 L 18.54669,-3.01594 L 9.16027,-1.13887 L 0,0 L 1.13887,9.16026 L 3.01594,18.54669 L 3.3219,27.26772 L 2.16406,31.10009 L -0.25254,34.43179 L -2.62949,35.93955 L -5.39453,36.45023 L -11.75746,35.46181 L -18.67852,33.42935 L -25.49492,32.31567 L -30.08874,33.6726 L -33.0107,37.40784 L -34.55105,43.01807 L -35,50 L -34.55105,56.98193 L -33.0107,62.59216 L -30.08874,66.3274 L -25.49492,67.68433 L -18.67852,66.57065 L -11.75746,64.53819 L -5.39453,63.54976 L -2.62949,64.06045 L -0.25254,65.56821 L 2.16406,68.89991 L 3.3219,72.73228 L 3.01594,81.453307 L 1.13887,90.839731 L 0,100 L 9.16027,101.13887 L 18.54669,103.01594 L 27.26772,103.3219 L 31.1001,102.16406 L 34.43179,99.74746 L 35.93955,97.37051 L 36.45024,94.605468 L 35.46181,88.242538 L 33.42935,81.321478 L 32.31567,74.505078 L 33.6726,69.91126 L 37.40784,66.9893 L 43.01808,65.44895 L 50,65 L 56.98193,65.44895 L 62.59216,66.9893 L 66.3274,69.91126 L 67.68433,74.505078 L 66.57065,81.321478 L 64.53819,88.242538 L 63.54977,94.605468 L 64.06045,97.37051 L 65.56821,99.74746 L 68.89991,102.16406 L 72.73228,103.3219 L 81.45331,103.01594 L 90.83974,101.13887 L 100,100 L 98.86113,90.839733 L 96.98407,81.453309 L 96.6781,72.732282 L 97.83594,68.8999 L 100.25254,65.56821 L 102.62949,64.06045 L 105.39453,63.54976 L 111.75746,64.53819 L 118.67852,66.57065 L 125.49492,67.68432 L 130.08874,66.3274 L 133.0107,62.59216 L 134.55105,56.98192 L 135,50 L 134.55105,43.01807 L 133.0107,37.40783 L 130.08874,33.6726 L 125.49492,32.31567 L 118.67852,33.42934 L 111.75746,35.46181 L 105.39453,36.45023 L 102.62949,35.93955 L 100.25254,34.43179 L 97.83594,31.10009 L 96.6781,27.26771 L 96.98407,18.54669 L 98.86113,9.16026 L 100,0 Z";
	String xmlEdgeIOI3 = "M 90.83973,98.86113 L 81.45331,96.98406 L 72.73228,96.6781 L 68.89991,97.83594 L 65.56821,100.25254 L 64.06045,102.62949 L 63.54976,105.39453 L 64.53819,111.75746 L 66.57065,118.67852 L 67.68433,125.49492 L 66.3274,130.08874 L 62.59216,133.0107 L 56.98193,134.55105 L 50,135 L 43.01807,134.55105 L 37.40784,133.0107 L 33.6726,130.08874 L 32.31567,125.49492 L 33.42935,118.67852 L 35.46181,111.75746 L 36.45023,105.39453 L 35.93955,102.62949 L 34.43179,100.25254 L 31.10009,97.83594 L 27.26772,96.6781 L 18.54669,96.98406 L 9.16026,98.86113 L 0,100 L -1.13887,90.83973 L -3.01594,81.45331 L -3.3219,72.73228 L -2.16406,68.8999 L 0.25254,65.56821 L 2.62949,64.06045 L 5.39453,63.54976 L 11.75746,64.53819 L 18.67852,66.57065 L 25.49492,67.68433 L 30.08874,66.3274 L 33.0107,62.59216 L 34.55105,56.98192 L 35,50 L 34.55105,43.01807 L 33.0107,37.40784 L 30.08874,33.6726 L 25.49492,32.31567 L 18.67852,33.42935 L 11.75746,35.46181 L 5.39453,36.45023 L 2.62949,35.93955 L 0.25254,34.43179 L -2.16406,31.10009 L -3.3219,27.26772 L -3.01594,18.54669 L -1.13887,9.16026 L 0,0 L 100,0 L 101.13887,9.16026 L 103.01594,18.54669 L 103.3219,27.26772 L 102.16406,31.10009 L 99.74746,34.43179 L 97.37051,35.93955 L 94.60547,36.45023 L 88.24254,35.46181 L 81.32148,33.42935 L 74.50508,32.31567 L 69.91126,33.6726 L 66.9893,37.40784 L 65.44895,43.01807 L 65,50 L 65.44895,56.98192 L 66.9893,62.59216 L 69.91126,66.3274 L 74.50508,67.68433 L 81.32148,66.57065 L 88.24254,64.53819 L 94.60547,63.54976 L 97.37051,64.06045 L 99.74746,65.56821 L 102.16406,68.8999 L 103.3219,72.73228 L 103.01594,81.45331 L 101.13887,90.83973 L 100,100 Z";
	String xmlEdgeOIO3 = "M 98.86113,90.83974 L 96.98407,81.453312 L 96.6781,72.732285 L 97.83594,68.899907 L 100.25254,65.568212 L 102.62949,64.060453 L 105.39453,63.549766 L 111.75746,64.538191 L 118.67852,66.570655 L 125.49492,67.684328 L 130.08874,66.327402 L 133.0107,62.592165 L 134.55105,56.981927 L 135,50.000001 L 134.55105,43.018075 L 133.0107,37.407837 L 130.08874,33.6726 L 125.49492,32.315674 L 118.67852,33.429347 L 111.75746,35.461811 L 105.39453,36.450236 L 102.62949,35.939549 L 100.25254,34.43179 L 97.83594,31.100095 L 96.6781,27.267717 L 96.98407,18.54669 L 98.86113,9.160266 L 100,9.9999999e-7 L 0,0 L 1.13887,9.16026 L 3.01594,18.54669 L 3.3219,27.26772 L 2.16406,31.10009 L -0.25254,34.43179 L -2.62949,35.93955 L -5.39453,36.45023 L -11.75746,35.46181 L -18.67852,33.42935 L -25.49492,32.31567 L -30.08874,33.6726 L -33.0107,37.40784 L -34.55105,43.01807 L -35,50 L -34.55105,56.98193 L -33.0107,62.59216 L -30.08874,66.3274 L -25.49492,67.68433 L -18.67852,66.57065 L -11.75746,64.53819 L -5.39453,63.54976 L -2.62949,64.06045 L -0.25254,65.56821 L 2.16406,68.89991 L 3.3219,72.732283 L 3.01594,81.45331 L 1.13887,90.83973 L 0,100 L 9.16027,101.13887 L 18.54669,103.01594 L 27.26772,103.3219 L 31.1001,102.16406 L 34.43179,99.74746 L 35.93955,97.37051 L 36.45024,94.60547 L 35.46181,88.242541 L 33.42935,81.321481 L 32.31567,74.505081 L 33.6726,69.911264 L 37.40784,66.989304 L 43.01808,65.448954 L 50,65.000004 L 56.98193,65.448954 L 62.59216,66.989304 L 66.3274,69.911264 L 67.68433,74.505081 L 66.57065,81.321481 L 64.53819,88.242541 L 63.54977,94.60547 L 64.06045,97.37051 L 65.56821,99.74746 L 68.89991,102.16406 L 72.73228,103.3219 L 81.45331,103.01594 L 90.83974,101.13887 L 100,100 Z";
	String xmlCorner3 = "M 98.86113,90.83973 L 96.98407,81.45331 L 96.6781,72.73228 L 97.83594,68.8999 L 100.25254,65.56821 L 102.62949,64.06045 L 105.39453,63.54976 L 111.75746,64.53819 L 118.67852,66.57065 L 125.49492,67.68432 L 130.08874,66.3274 L 133.0107,62.59216 L 134.55105,56.98192 L 135,50 L 134.55105,43.01807 L 133.0107,37.40783 L 130.08874,33.6726 L 125.49492,32.31567 L 118.67852,33.42934 L 111.75746,35.46181 L 105.39453,36.45023 L 102.62949,35.93955 L 100.25254,34.43179 L 97.83594,31.10009 L 96.6781,27.26771 L 96.98407,18.54669 L 98.86113,9.16026 L 100,0 L 0,0 L 0,100 L 9.16027,101.13887 L 18.54669,103.01594 L 27.26772,103.3219 L 31.1001,102.16406 L 34.43179,99.74746 L 35.93955,97.37051 L 36.45024,94.60547 L 35.46181,88.24254 L 33.42935,81.32148 L 32.31567,74.50508 L 33.6726,69.91126 L 37.40784,66.9893 L 43.01808,65.44895 L 50,65 L 56.98193,65.44895 L 62.59216,66.9893 L 66.3274,69.91126 L 67.68433,74.50508 L 66.57065,81.32148 L 64.53819,88.24254 L 63.54977,94.60547 L 64.06045,97.37051 L 65.56821,99.74746 L 68.89991,102.16406 L 72.73228,103.3219 L 81.45331,103.01594 L 90.83974,101.13887 L 100,100 Z";

	Array<Piece> pieces = new Array<>();
	boolean drawGrid = true;

	public XMLPathTest (GameReset game) {
		super(game);
		clear.set(Color.GRAY);
		gameCamera.zoom = .25f;
		gameCamera.update();
//		pieces.add(new Piece("box", parsePath(xmlBox), 0f, 3f));
		pieces.add(new Piece("inner", parsePath(xmlInner3), -4f, 0f));
		pieces.add(new Piece("edgeIOI", parsePath(xmlEdgeIOI3), -2f, 0f));
		pieces.add(new Piece("edgeOIO", parsePath(xmlEdgeOIO3), 0f, 0f));
		pieces.add(new Piece("corner", parsePath(xmlCorner3), 2f, 0f));
	}

	protected static Array<Vector2> parsePath (String path) {
		Array<Vector2> points = new Array<>();
		char[] chars = path.toCharArray();
		char[] temp = new char[8 + 1 + 8];
		for (int i = 0; i < chars.length;) {
			char aChar = chars[i++];
			if (aChar == ' ') continue;
			if (aChar == 'M' || aChar == 'L') {
				// we have new point
				char next;
				int len = 0;
				while (i < chars.length) {
					next = chars[i++];
					if (next == ' ') continue;
					if (next == ',') {
						break;
					}
					temp[len++] = next;
				}
				float x = Float.parseFloat(new String(temp, 0, len));
				len = 0;
				while (i < chars.length) {
					next = chars[i++];
					if (next == ' ') {
						i--;
						break;
					}
					temp[len++] = next;
				}
				float y = Float.parseFloat(new String(temp, 0, len));
				// dunno wtf is up this this translate, needed to move y to 0
//				points.add(new Vector2(x * INV_SCALE, (y -952.36216f) * INV_SCALE));
				// -y + 100 to flip it
//				points.add(new Vector2(x, (-y +952.36216f + 100)));
				points.add(new Vector2(x, (-y + 100)));
			} else if (aChar == 'Z') {
//				points.add(points.get(0).cpy());
			}
		}
		// BayazitDecomposer doesnt like duplicate points
		if (points.first().epsilonEquals(points.get(points.size -1), 0.001f)) {
			points.removeIndex(points.size -1);
		}
		IntArray remove = new IntArray();

		for (int i = 0; i < points.size; i++) {
			for (int j = 0; j < points.size; j++) {
				if (i == j) continue;
				Vector2 p1 = points.get(i);
				Vector2 p2 = points.get(j);
				if (p1.epsilonEquals(p2, .001f)) {
					if (!remove.contains(i) && !remove.contains(j))
						remove.add(i);
				}
			}
		}
		remove.sort();
		remove.reverse();
		for (int i = 0; i < remove.size; i++) {
			points.removeIndex(remove.get(i));
		}
		return points;
	}

	@Override public void render (float delta) {
		super.render(delta);
		enableBlending();
		renderer.setProjectionMatrix(gameCamera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		if (drawGrid) {
			renderer.setColor(1, 1, 1, .25f);
			float sx = (int)(-VP_WIDTH/2);
			float sy = (int)(-VP_HEIGHT/2);
			int width = MathUtils.ceil(VP_WIDTH);
			int height = MathUtils.ceil(VP_HEIGHT);
			for (int x = 0; x <= width; x++) {
				renderer.line(sx + x, sy, sx + x, sy + height);
			}
			for (int y = 0; y <= height; y++) {
				renderer.line(sx, sy + y, sx + width, sy + y);
			}
		}

		for (Piece piece : pieces) {
			piece.draw(renderer);
		}

		renderer.end();
	}

	protected static class Piece {
		Vector2 pos = new Vector2();
		String name;
		Array<Vector2> rawPoints = new Array<>();
		Array<Array<Vector2>> rawPolygons = new Array<>();
		Color[] colors;
		Array<Polygon> polygons = new Array<>();

		public Piece (String name, Array<Vector2> rawPoints, float x, float y) {
			this.name = name;
			this.rawPoints.addAll(rawPoints);
			pos.set(x, y);

			rawPolygons = BayazitDecomposer.convexPartition(rawPoints);
			colors = new Color[rawPolygons.size];
			for (int i = 0; i < rawPolygons.size; i++) {
				colors[i] = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
			}

			System.out.println("PolygonPiece "+name+" = new PolygonPiece(");

//				new Polygon(new float[] {33.42935f, 81.32148f, 35.46181f, 88.24254f, 36.45024, 94.60547, 35.93955, 97.370514}, new short[] {1, 3, 2, 1, 0, 3}),

			DelaunayTriangulator dt = new DelaunayTriangulator();
			for (int j = 0; j < rawPolygons.size; j++) {
				Array<Vector2> rawPolygon = rawPolygons.get(j);
				Polygon polygon = new Polygon();
				polygons.add(polygon);
				polygon.vertices = new float[rawPolygon.size * 2];
				for (int i = 0; i < rawPolygon.size; i++) {
					Vector2 p = rawPolygon.get(i);
					polygon.vertices[i * 2] = p.x;
					polygon.vertices[i * 2 + 1] = p.y;
				}
				polygon.indices = new ShortArray(dt.computeTriangles(polygon.vertices, false));

				System.out.print("\tnew Polygon(new float[] {");
				for (int i = 0; i < polygon.vertices.length; i++) {
					System.out.print(polygon.vertices[i] + "f");
					if (i < polygon.vertices.length -1) {
						System.out.print(", ");
					}
				}
				System.out.print("}, new short[] {");
				for (int i = 0; i < polygon.indices.size; i++) {
					System.out.print(polygon.indices.get(i));
					if (i < polygon.indices.size -1) {
						System.out.print(", ");
					}
				}
				System.out.print("})");
				if (j < rawPolygons.size -1) {
					System.out.println(", ");
				} else {
					System.out.println("");
				}
			}
			System.out.println(");");
		}

		float scale = .01f;
		float down = 2f;
		public void draw (ShapeRenderer renderer) {
			float cos = (float)Math.cos(90 * MathUtils.degreesToRadians);
			float sin = (float)Math.sin(90 * MathUtils.degreesToRadians);

//			float newX = this.x * cos - this.y * sin;
//			float newY = this.x * sin + this.y * cos;
			Vector2 p1;
			Vector2 p2;
			renderer.setColor(Color.CYAN);
			for (int i = 0; i < rawPoints.size -1; i++) {
				p1 = rawPoints.get(i);
				p2 = rawPoints.get(i + 1);
				float x1 = pos.x + (p1.x - 50) * scale * cos - (p1.y - 50) * scale * sin + 50 * scale;
				float y1 = pos.y + (p1.x - 50) * scale * sin + (p1.y - 50) * scale * cos + 50 * scale;
				float x2 = pos.x + (p2.x - 50) * scale * cos - (p2.y - 50) * scale * sin + 50 * scale;
				float y2 = pos.y + (p2.x - 50) * scale * sin + (p2.y - 50) * scale * cos + 50 * scale;
				renderer.line(x1, y1, x2, y2);
//				p1.sub(50, 50).rotate(90).add(50, 50);
//				p2.sub(50, 50).rotate(90).add(50, 50);
//				renderer.line(pos.x + p1.x * scale, pos.y + p1.y * scale, pos.x + p2.x * scale, pos.y + p2.y * scale);
//				p1.sub(50, 50).rotate(-90).add(50, 50);
//				p2.sub(50, 50).rotate(-90).add(50, 50);
			}
			p1 = rawPoints.get(0);
			p2 = rawPoints.get(rawPoints.size -1);
			float x1 = pos.x + (p1.x - 50) * scale * cos - (p1.y - 50) * scale * sin + 50 * scale;
			float y1 = pos.y + (p1.x - 50) * scale * sin + (p1.y - 50) * scale * cos + 50 * scale;
			float x2 = pos.x + (p2.x - 50) * scale * cos - (p2.y - 50) * scale * sin + 50 * scale;
			float y2 = pos.y + (p2.x - 50) * scale * sin + (p2.y - 50) * scale * cos + 50 * scale;
			renderer.line(x1, y1, x2, y2);

//			for (int i = 0; i < rawPolygons.size; i++) {
//				renderer.setColor(colors[i]);
//				Array<Vector2> polygon = rawPolygons.get(i);
//				for (int j = 0; j < polygon.size - 1; j++) {
//					Vector2 p1 = polygon.get(j);
//					Vector2 p2 = polygon.get(j + 1);
//					renderer.line(pos.x + p1.x * scale, pos.y + p1.y * scale - down, pos.x + p2.x * scale, pos.y + p2.y * scale - down);
//					if (j == polygon.size -2) {
//						p1 = polygon.get(0);
//						renderer.line(pos.x + p1.x * scale, pos.y + p1.y * scale - down, pos.x + p2.x * scale, pos.y + p2.y * scale - down);
//					}
//				}
//			}

//			renderer.setColor(Color.GREEN);
//			for (Polygon polygon : polygons) {
//				float[] vertices = polygon.vertices;
//				for (int i = 0; i < vertices.length -2; i+=2) {
//					float x1 = vertices[i];
//					float y1 = vertices[i + 1];
//					float x2 = vertices[i + 2];
//					float y2 = vertices[i + 3];
//					renderer.line(pos.x + x1 * scale, pos.y + y1 * scale - down, pos.x + x2 * scale, pos.y + y2 * scale - down);
//				}
//				float x1 = vertices[0];
//				float y1 = vertices[1];
//				float x2 = vertices[vertices.length -2];
//				float y2 = vertices[vertices.length -1];
//				renderer.line(pos.x + x1 * scale, pos.y + y1 * scale - down, pos.x + x2 * scale, pos.y + y2 * scale - down);
//
//			}

			renderer.end();
			renderer.begin(ShapeRenderer.ShapeType.Filled);

			for (Polygon polygon : polygons) {
				float[] vertices = polygon.vertices;
				ShortArray indices = polygon.indices;
				for (int i = 0; i < indices.size; i+=3) {
					int i1 = indices.get(i) * 2;
					int i2 = indices.get(i + 1) * 2;
					int i3 = indices.get(i + 2) * 2;
					renderer.triangle(
						pos.x + vertices[i1] * scale, pos.y + vertices[i1 + 1] * scale - down,
						pos.x + vertices[i2] * scale, pos.y + vertices[i2 + 1] * scale - down,
						pos.x + vertices[i3] * scale, pos.y + vertices[i3 + 1] * scale - down
					);
				}
			}

			renderer.end();
			renderer.begin(ShapeRenderer.ShapeType.Line);
			renderer.setColor(Color.MAGENTA);
			for (int i = 0; i < rawPolygons.size; i++) {
				Array<Vector2> polygon = rawPolygons.get(i);
				for (int j = 0; j < polygon.size - 1; j++) {
					p1 = polygon.get(j);
					p2 = polygon.get(j + 1);
					renderer.line(pos.x + p1.x * scale, pos.y + p1.y * scale - down, pos.x + p2.x * scale, pos.y + p2.y * scale - down);
					if (j == polygon.size -2) {
						p1 = polygon.get(0);
						renderer.line(pos.x + p1.x * scale, pos.y + p1.y * scale - down, pos.x + p2.x * scale, pos.y + p2.y * scale - down);
					}
				}
			}
		}

		static class Polygon {
			public float[] vertices;
			public ShortArray indices;
		}
	}

	private void draw (Array<Vector2> points, float ox, float oy) {
		for (int i = 0; i < points.size -1; i++) {
			Vector2 p1 = points.get(i);
			Vector2 p2 = points.get(i + 1);
			renderer.line(p1.x + ox, p1.y + oy, p2.x + ox, p2.y + oy);
		}
	}

	// allow us to start this test directly
	public static void main (String[] args) {
		PlaygroundGame.start(args, XMLPathTest.class);
	}
}
