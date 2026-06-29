import { useEffect, useState } from "react";
import { listarVehiculos } from "../../services/vehiculoService";
import "../Vehiculos/VehiculoForm.css";

function SoapForm({ onSubmit, soap }) {

    const [vehiculos, setVehiculos] = useState([]);

    const initialForm = {
        vehiculoId: "",
        aseguradora: "",
        numeroPoliza: "",
        fechaInicio: "",
        fechaVencimiento: "",
        cobertura: ""
    };

    const [form, setForm] = useState(initialForm);

    useEffect(() => {
        cargarVehiculos();
    }, []);

    useEffect(() => {

        if (soap) {
            setForm({
                vehiculoId: soap.vehiculoId || "",
                aseguradora: soap.aseguradora || "",
                numeroPoliza: soap.numeroPoliza || "",
                fechaInicio: soap.fechaInicio || "",
                fechaVencimiento: soap.fechaVencimiento || "",
                cobertura: soap.cobertura || ""
            });
        } else {
            setForm(initialForm);
        }

    }, [soap]);

    const cargarVehiculos = async () => {

        const response = await listarVehiculos();

        setVehiculos(response.data);

    };

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        onSubmit(form);

    };

    return (

        <form className="vehiculo-form" onSubmit={handleSubmit}>

            <select
                name="vehiculoId"
                value={form.vehiculoId}
                onChange={handleChange}
            >
                <option value="">Seleccione Vehículo</option>

                {vehiculos?.map((v) => (
                    <option key={v.id} value={v.id}>
                        {v.patente} - {v.marca}
                    </option>
                ))}

            </select>

            <input
                name="aseguradora"
                placeholder="Aseguradora"
                value={form.aseguradora}
                onChange={handleChange}
            />

            <input
                name="numeroPoliza"
                placeholder="Número póliza"
                value={form.numeroPoliza}
                onChange={handleChange}
            />

            <input
                type="date"
                name="fechaInicio"
                value={form.fechaInicio}
                onChange={handleChange}
            />

            <input
                type="date"
                name="fechaVencimiento"
                value={form.fechaVencimiento}
                onChange={handleChange}
            />

            <input
                name="cobertura"
                placeholder="Cobertura"
                value={form.cobertura}
                onChange={handleChange}
            />

            <button type="submit">
                {soap ? "Actualizar" : "Guardar"}
            </button>

        </form>

    );
}

export default SoapForm;