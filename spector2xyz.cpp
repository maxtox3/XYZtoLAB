#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>

std::vector<double> loadDB(const char* filename)
{
    std::vector<double> tmpVec;

    std::ifstream strFile;

    strFile.open(filename);
    if(!strFile)
    {
        throw std::runtime_error("No data base file");
    }

    double value;

    while(strFile >> value)
    {
        tmpVec.push_back(value);
    }

    strFile.close();

    return tmpVec;
}



int main()
{
    std::vector<double> CieCmfXYZ = loadDB("cie-cmf.txt");  // xyz dash
    std::vector<double> palette   = loadDB("Orange_1.txt"); // F
    std::vector<double> light     = loadDB("light.txt");

    std::vector<double> final;

    for (int i = 0; i != palette.size(); i++)
    {

        final.push_back(palette[i] * light[i]);

    }
    

    double sumX = 0;
    double sumY = 0;
    double sumZ = 0;

    int spec = 380;

    for (std::vector<double>::iterator iterCieCmf = CieCmfXYZ.begin(), iterPalette = final.begin(); iterCieCmf != CieCmfXYZ.end();)
    {
        sumX += spec * (*iterPalette) * (*iterCieCmf) * spec;
        sumY += spec * (*iterPalette) * (*(iterCieCmf + 1)) * spec;
        sumZ += spec * (*iterPalette) * (*(iterCieCmf + 2)) * spec;

        spec += 10;
        std::advance(iterCieCmf, 3);
        iterPalette++;
    }

    double k = 100 / (sumY * 10);


    double X = k * sumX * 10;
    double Y = k * sumY * 10;
    double Z = k * sumZ * 10;



    // XYZ to RGB

    	X = X / 100.0;
	    Y = Y / 100.0;
	    Z = Z / 100.0;

            std::cout << "X: " << X << " Y: " << Y << " Z: " << Z << std::endl;

	    double var_R = X * 3.2404542 + Y * -1.5371385 + Z * -0.4985314;
	    double var_G = X * -0.9692660 + Y * 1.8760108 + Z * 0.0415560;
	    double var_B = X * 0.0556434 + Y * -0.2040259 + Z * 1.0572252;

	    var_R = ((var_R > 0.0031308) ? (1.055*pow(var_R, 1 / 2.4) - 0.055) : (12.92*var_R)) * 255.0;
	    var_G = ((var_G > 0.0031308) ? (1.055*pow(var_G, 1 / 2.4) - 0.055) : (12.92*var_G)) * 255.0;
	    var_B = ((var_B > 0.0031308) ? (1.055*pow(var_B, 1 / 2.4) - 0.055) : (12.92*var_B)) * 255.0;

    std::cout << "R: " << var_R << " G: " << var_G << " B: " << var_B << std::endl;

    return 0;
}
