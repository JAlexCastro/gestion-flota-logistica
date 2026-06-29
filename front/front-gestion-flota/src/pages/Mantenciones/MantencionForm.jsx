import { useEffect, useState } from "react";
import { listarVehiculos } from "../../services/vehiculoService";
import "../Vehiculos/VehiculoForm.css";

function MantencionForm({ onSubmit, mantencion }) {

    const [vehiculos, setVehiculos] = useState([]);

    const initialForm = {
        vehiculoId: "",
        fecha: "",
        kilometraje: "",
        tipo: "",
        descripcion: "",
        taller: ""
    };

    const [form, setForm] = useState(initialForm);

    // Cargar vehículos
    useEffect(() => {
        cargarVehiculos();
    }, []);

    // Cargar datos para edición o resetear
    useEffect(() => {

        if (mantencion) {
            setForm({
                vehiculoId: mantencion.vehiculoId || "",
                fecha: mantencion.fecha || "",
                kilometraje: mantencion.kilometraje || "",
                tipo: mantencion.tipo || "",
                descripcion: mantencion.descripcion || "",
                taller: mantencion.taller || ""
            });
        } else {
            setForm(initialForm);
        }

    }, [mantencion]);

    const cargarVehiculos = async () => {

    try {
        const response = await listarVehiculos();

        console.log("API RESPONSE:", response);

        const lista = response.data;

        setVehiculos(Array.isArray(lista) ? lista : []);

    } catch (error) {
        console.error("Error cargando vehículos:", error);
        setVehiculos([]);
    }

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
                type="date"
                name="fecha"
                value={form.fecha}
                onChange={handleChange}
            />

            <input
                type="number"
                name="kilometraje"
                placeholder="Kilometraje"
                value={form.kilometraje}
                onChange={handleChange}
            />

            <input
                type="text"
                name="tipo"
                placeholder="Tipo"
                value={form.tipo}
                onChange={handleChange}
            />

            <input
                type="text"
                name="taller"
                placeholder="Taller"
                value={form.taller}
                onChange={handleChange}
            />

            <input
                type="text"
                name="descripcion"
                placeholder="Descripción"
                value={form.descripcion}
                onChange={handleChange}
            />

            <button type="submit">
                {mantencion ? "Actualizar" : "Guardar"}
            </button>

        </form>

    );
}

export default MantencionForm;